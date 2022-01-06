package com.tangerinespecter.oms.system.service.tools.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tangerinespecter.oms.common.utils.HttpUtils;
import com.tangerinespecter.oms.system.domain.pojo.BingImageResponse;
import com.tangerinespecter.oms.system.domain.vo.tools.BingImageReq;
import com.tangerinespecter.oms.system.mapper.DataWallPageMapper;
import com.tangerinespecter.oms.system.service.tools.IEveryDayWallPaperToolService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

@Service
public class EveryDayWallPaperToolServiceImpl implements IEveryDayWallPaperToolService {
	
	@Value("${bing.wall.paper.url}")
	private String bingWallPaperUrl;
	
	@Override
	public BingImageResponse wallPagerInfo(BingImageReq req) {
		Map<String, Object> params = JSONObject.parseObject(JSON.toJSONString(req));
		String result = HttpUtils.interfaceInvoke(bingWallPaperUrl, params, "GET");
		return JSONObject.toJavaObject(JSON.parseObject(result), BingImageResponse.class);
	}
}
