package com.tangerineSpecter.oms.job.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.tangerineSpecter.oms.common.constant.CommonConstant;
import com.tangerineSpecter.oms.common.utils.DateUtils;
import com.tangerineSpecter.oms.common.utils.HttpUtils;
import com.tangerineSpecter.oms.common.utils.ParamUtils;
import com.tangerineSpecter.oms.system.domain.DataConstellation;
import com.tangerineSpecter.oms.system.mapper.DataConstellationMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConstellationQuartzService {

	/** 星座列表 */
	List<String> starList = Arrays.asList("白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座",
			"双鱼座");

	private final String TODAY = "today";
	@Autowired
	private DataConstellationMapper dataConstellationMapper;
	/** 成功错误码 */
	private final String SUCCESS_CODE = "0";

	/**
	 * 执行星座数据
	 * 
	 * @throws Exception
	 */
	@Transactional
	public void runData() {
		log.info("[执行星座数据写入任务]");
		// 待处理星座数据
		List<String> noStarList = new ArrayList<>();
		// 插入条数
		int count = 0;
		try {
			String today = DateUtils.timeFormatToDay(new Date());
			List<String> list = dataConstellationMapper.queryListByCreateTime(today);
			for (String star : starList) {
				if (!list.contains(star)) {
					noStarList.add(star);
				}
			}
			if (noStarList.isEmpty()) {
				log.info("[星座定时任务执行完毕，当前没有需要插入的数据]");
				return;
			}
			for (String star : noStarList) {
				Map<String, Object> configBean = new HashMap<>();
				configBean.put(ParamUtils.CONSNAME, star);
				configBean.put(ParamUtils.KEY, CommonConstant.JUHE_API_CONSTELLATION_KEY);
				configBean.put(ParamUtils.TYPE, TODAY);
				String result = HttpUtils.interfaceInvoke(CommonConstant.JUHE_API_CONSTELLATION_URL, configBean,
						"POST");
				log.info(String.format("[请求星座API成功获取数据]:star:{},result:{}", star, result));
				JSONObject starObj = JSONObject.parseObject(result);
				String resultcode = starObj.getString("error_code");
				if (resultcode.equals(SUCCESS_CODE)) {
					dataConstellationMapper.insert(getDateConstellation(starObj));
					count++;
				} else {
					log.error(String.format("[星座接口请求数据异常],error_code:{}; reason:{}", starObj.getString("error_code"),
							starObj.getString("reason")));
				}
			}
			log.info(String.format("[星座定时任务执行完毕],插入数据：{}", count));
		} catch (Exception e) {
			log.warn(String.format("[星座接口调用异常]:", e));
		}
	}

	/**
	 * 获取星座对象
	 * 
	 */
	private DataConstellation getDateConstellation(JSONObject starObj) {
		DataConstellation dc = new DataConstellation();
		dc.setName(starObj.getString("name"));
		dc.setDateTime(starObj.getString("datetime"));
		dc.setDate(starObj.getInteger("date"));
		dc.setAllLuck(starObj.getString("all"));
		dc.setColor(starObj.getString("color"));
		dc.setHealth(starObj.getString("health"));
		dc.setLove(starObj.getString("love"));
		dc.setMoney(starObj.getString("money"));
		dc.setNumber(starObj.getInteger("number"));
		dc.setQFriend(starObj.getString("QFriend"));
		dc.setWorkLuck(starObj.getString("work"));
		dc.setSummary(starObj.getString("summary"));
		dc.setCreateTime(DateUtils.timeFormatToDay(new Date()));
		return dc;
	}
}
