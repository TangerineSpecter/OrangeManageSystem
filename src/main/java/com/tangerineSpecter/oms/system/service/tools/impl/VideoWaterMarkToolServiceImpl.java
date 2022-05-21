package com.tangerinespecter.oms.system.service.tools.impl;

import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.VideoWatermarkInfoVo;
import com.tangerinespecter.oms.system.service.tools.IVideoWaterMarkToolService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.regex.Matcher;

/**
 * @author TangerineSpecter
 */
@Slf4j
@Service
public class VideoWaterMarkToolServiceImpl implements IVideoWaterMarkToolService {

    /**
     * 长链接 + itemId
     */
    private static final String LONG_URL = "https://www.iesdouyin.com/web/api/v2/aweme/iteminfo/?item_ids={}";
    /**
     * 展示URL + vid
     */
    private static final String VIEW_URL = "https://aweme.snssdk.com/aweme/v1/play/?video_id={}&line=0&ratio=540p&media_type=4&vr_type=0&improve_bitrate=0&is_play_url=1&is_support_h265=0&source=PackSourceEnum_PUBLISH";

    @Override
    public ServiceResult clearVideoWatermark(VideoWatermarkInfoVo vo) throws Exception {
        //清洗出地址中的url
        Matcher matcher = PatternPool.URL.matcher(vo.getUrl());
        if (matcher.find()) {
            vo.setUrl(matcher.group());
        }
        if (!checkUrl(vo.getUrl())) {
            return ServiceResult.error(RetCode.VIDEO_URL_ERROR);
        }
        try {
            Document document = Jsoup.connect(vo.getUrl()).followRedirects(true).execute().parse();
            String jumpUrl = document.baseUri();
            //参数清洗
            List<String> splitUrl = StrUtil.split(jumpUrl, "?");
            String baseUrl = splitUrl.get(0);
            //获取itemId
            String itemId = CharSequenceUtil.split(baseUrl, "video/").get(1);
            //组装视频长链接
            String jsonData = HttpUtil.get(CharSequenceUtil.format(LONG_URL, itemId));
            //执行JSON数据清洗
            JSONObject jsonObj = JSON.parseObject(jsonData);
            JSONArray list = jsonObj.getJSONArray("item_list");
            JSONObject jsonObject = list.getJSONObject(0);
            JSONObject video = jsonObject.getJSONObject("video");
            String vid = video.getString("vid");
            //组装无水印观看地址
            return ServiceResult.success(CharSequenceUtil.format(VIEW_URL, vid));
        } catch (Exception e) {
            log.error(e + e.getMessage());
            return ServiceResult.error(RetCode.VIDEO_URL_ERROR);
        }
    }

    /**
     * Url图片有效性校验
     *
     * @return 有效返回true，无效返回false
     */
    private static boolean checkUrl(String content) {
        try {
            URL url = new URL(content);
            final int successCode = 200;
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            int responseCode = con.getResponseCode();
            return responseCode == successCode;
        } catch (Exception e) {
            log.error(e + e.getMessage());
            return false;
        }
    }

}
