package com.tangerinespecter.oms.job.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.alibaba.fastjson.JSONObject;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.utils.DateUtils;
import com.tangerinespecter.oms.common.utils.HttpUtils;
import com.tangerinespecter.oms.common.utils.ParamUtils;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.domain.enums.StarNameEnum;
import com.tangerinespecter.oms.system.mapper.DataConstellationMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class ConstellationQuartzService {

    /**
     * 聚合数据接口Key
     */
    @Value("${juhe.api.key}")
    public String juheApiUserKey;
    /**
     * 聚合数据--星座key
     */
    @Value("${juhe.api.constellation.key}")
    public String juheApiKey;
    /**
     * 聚合数据--星座Url
     */
    @Value("${juhe.api.constellation.url}")
    public String juheApiUrl;

    /**
     * 星座列表
     */
    List<String> starList = Arrays.asList("白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座", "双鱼座");

    /**
     * 第一星座网URL + 星座 EN-NAME
     */
    private static final String D1XZ_URL = "https://www.d1xz.net/yunshi/today/{}";

    private final String TODAY = "today";
    @Resource
    private DataConstellationMapper dataConstellationMapper;
    /**
     * 成功错误码
     */
    private final String SUCCESS_CODE = "0";

    /**
     * 执行星座数据
     */
    public void runData() {
        log.info("[执行星座数据写入任务]");
        try {
            List<String> noStarList = this.getNoStarList();
            if (noStarList.isEmpty()) {
                log.info("[星座定时任务执行完毕，当前没有需要插入的数据]");
                return;
            }
            this.handleStarData(noStarList);
            log.info("[星座定时任务执行完毕]");
        } catch (Exception e) {
            log.warn("[星座接口调用异常]:", e);
        }
    }

    /**
     * 获取未入库的星座数据列表
     *
     * @return 星座列表
     */
    private List<String> getNoStarList() {
        List<String> existStars = dataConstellationMapper.queryListByCreateTime(DateUtil.today());
        //待处理星座数据
        return CollUtil.subtractToList(starList, existStars);
    }

    private void handleStarData(List<String> noStarList) {
        try {
            log.info("[开始爬取星座数据，需要爬取数量：{}]", noStarList.size());
            this.spiderD1xzData(noStarList);
            return;
        } catch (Exception e) {
            log.error("[爬取星座数据异常]：" + e.getMessage());
        }
        log.info("开始通过api获取星座数据");
        List<String> apiKeys = CharSequenceUtil.split(juheApiKey, ";");
        this.handleStarData(this.getNoStarList(), apiKeys, 0);
    }

    /**
     * 处理星座数据
     *
     * @param noStarList 未处理的星座列表
     * @param apiKeys    api的key列表
     * @param keyNumber  key数值
     */
    private void handleStarData(List<String> noStarList, List<String> apiKeys, int keyNumber) {
        String apiKey = CollUtil.get(apiKeys, keyNumber);
        if (CharSequenceUtil.isEmpty(apiKey)) {
            return;
        }
        Map<String, Object> configBean = new HashMap<>(16);
        configBean.put(ParamUtils.KEY, apiKey);
        configBean.put(ParamUtils.TYPE, TODAY);
        for (String star : noStarList) {
            configBean.put(ParamUtils.CONS_NAME, star);
            String result = HttpUtils.interfaceInvoke(juheApiUrl, configBean,
                    "POST");
            log.info("[请求星座API成功获取数据]:apiKey:{},star:{},result:{}", apiKey, star, result);
            JSONObject starObj = JSONObject.parseObject(result);
            String resultcode = starObj.getString("error_code");
            if (resultcode.equals(SUCCESS_CODE)) {
                dataConstellationMapper.insert(getDateConstellation(starObj));
//                count++;
            } else {
                log.error("[星座接口请求数据异常],error_code:{}; reason:{}", starObj.getString("error_code"), starObj.getString("reason"));
                this.handleStarData(noStarList, apiKeys, ++keyNumber);
            }
        }
    }

    /**
     * 获取星座对象
     */
    private DataConstellation getDateConstellation(JSONObject starObj) {
        DataConstellation dc = new DataConstellation();
        dc.setName(starObj.getString("name"));
        dc.setDatetime(starObj.getString("datetime"));
        dc.setDate(starObj.getInteger("date"));
        dc.setAllLuck(starObj.getInteger("all"));
        dc.setColor(starObj.getString("color"));
        dc.setHealth(starObj.getInteger("health"));
        dc.setLove(starObj.getInteger("love"));
        dc.setMoney(starObj.getInteger("money"));
        dc.setNumber(starObj.getInteger("number"));
        dc.setQfriend(starObj.getString("QFriend"));
        dc.setWorkLuck(starObj.getInteger("work"));
        dc.setSummary(starObj.getString("summary"));
        dc.setCreateTime(DateUtils.timeFormatToDay(new Date()));
        return dc;
    }

    /**
     * 爬取星座网站数据
     *
     * @param noStarList 未入库的星座列表
     */
    public void spiderD1xzData(List<String> noStarList) throws Exception {
        for (String star : noStarList) {
            this.runSpider(star);
            Thread.sleep(1000);
        }
    }

    /**
     * 运行爬虫
     *
     * @param star 星座中文名字
     */
    private void runSpider(String star) throws Exception {
        Document document = Jsoup.connect(CharSequenceUtil.format(D1XZ_URL, StarNameEnum.chnName2EnName(star))).get();
        //运势
        Elements luckElements = document.getElementsByClass("fraction").get(0).getElementsByTag("div");
        DataConstellation dc = new DataConstellation();
        dc.setName(star);
        dc.setLove(getStarInfoNumber(luckElements, 0));
        dc.setHealth(getStarInfoNumber(luckElements, 1));
        dc.setMoney(getStarInfoNumber(luckElements, 2));
        dc.setWorkLuck(getStarInfoNumber(luckElements, 3));
        dc.setAllLuck(getStarInfoNumber(luckElements, 4));
        String summary = document.getElementsByClass("txt").get(0).getElementsByTag("p").get(0).text();
        dc.setSummary(summary);
        Elements extraElements = document.getElementsByClass("quan_yuan").get(0).getElementsByTag("li");
        dc.setColor(getExtraInfo(extraElements, 0));
        dc.setNumber(Convert.toInt(getExtraInfo(extraElements, 1)));
        dc.setQfriend(getExtraInfo(extraElements, 2));
        dc.setCreateTime(DateUtils.timeFormatToDay(new Date()));
        dc.setDate(Convert.toInt(DateUtil.format(new Date(), DatePattern.PURE_DATE_PATTERN)));
        dc.setDatetime(DateUtil.format(new Date(), DatePattern.CHINESE_DATE_PATTERN));
        dataConstellationMapper.insert(dc);
    }

    /**
     * 获取额外信息
     * 0:颜色；1:数字；2:配对星座
     *
     * @return 信息
     */
    private static String getExtraInfo(Elements extraElements, int code) {
        return extraElements.get(code).getElementsByClass("words_t").get(0).text();
    }

    /**
     * 获取星座信息数值
     * 0：爱情；1：健康；2：财运；3：工作；4：综合
     *
     * @return 数值
     */
    public static Integer getStarInfoNumber(Elements luckElements, int code) {
        return Convert.toInt(CharSequenceUtil.replace(luckElements.get(code).getElementsByTag("strong").text(), "%", CommonConstant.NULL_KEY_STR));
    }
}
