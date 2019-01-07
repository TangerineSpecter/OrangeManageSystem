package com.tangerineSpecter.oms.job.service;

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
import com.tangerineSpecter.oms.job.domain.DataConstellation;
import com.tangerineSpecter.oms.job.mapper.DataConstellationMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ConstellationService {

	/** 星座列表 */
	List<String> starList = Arrays.asList("白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "摩羯座", "水瓶座",
			"双鱼座");

	private final String TODAY = "today";
	@Autowired
	private DataConstellationMapper dataConstellationMapper;

	/**
	 * 执行星座数据
	 * 
	 * @throws Exception
	 */
	@Transactional
	public void runData() {
		log.info("[执行星座数据写入任务]");
		try {
			String today = DateUtils.timeFormatToDay(new Date());
			for (String star : starList) {
				Map<String, Object> configBean = new HashMap<>();
				configBean.put(ParamUtils.CONSNAME, star);
				configBean.put(ParamUtils.KEY, CommonConstant.JUHE_API_CONSTELLATION_KEY);
				configBean.put(ParamUtils.TYPE, TODAY);
				String result = HttpUtils.interfaceInvoke(CommonConstant.JUHE_API_CONSTELLATION_URL, configBean);
				System.out.println(result);
				JSONObject starObj = JSONObject.parseObject(result);
				dataConstellationMapper.insert(getDateConstellation(starObj));
			}
			log.info("[星座定时任务执行完毕]");
		} catch (Exception e) {
			log.warn("星座接口调用异常:%s", e);
		}
	}

	/**
	 * 获取星座对象
	 * 
	 */
	private DataConstellation getDateConstellation(JSONObject starObj) {
		DataConstellation dc = new DataConstellation();
		dc.setName(starObj.getString("name"));
		dc.setDateTime(starObj.getString("dateTime"));
		dc.setDate(starObj.getInteger("date"));
		dc.setAll(starObj.getString("all"));
		dc.setColor(starObj.getString("color"));
		dc.setHealth(starObj.getString("health"));
		dc.setLove(starObj.getString("love"));
		dc.setMoney(starObj.getString("money"));
		dc.setNumber(starObj.getInteger("number"));
		dc.setQFriend(starObj.getString("QFriend"));
		dc.setWork(starObj.getString("work"));
		dc.setSummary(starObj.getString("summary"));
		dc.setCreateTime(DateUtils.timeFormatToDay(new Date()));
		return dc;
	}
}
