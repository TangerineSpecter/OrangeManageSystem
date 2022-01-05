package com.tangerinespecter.oms.system.service.tools.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.tangerinespecter.oms.common.utils.HttpUtils;
import com.tangerinespecter.oms.system.domain.entity.DataWallPage;
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
    @Resource
    private DataWallPageMapper dataWallPageMapper;

    @Override
    public BingImageResponse wallPagerInfo(BingImageReq req) {
        Map<String, Object> params = JSONObject.parseObject(JSON.toJSONString(req));
        String result = HttpUtils.interfaceInvoke(bingWallPaperUrl, params, "GET");
        BingImageResponse bingImageResponse = JSONObject.toJavaObject(JSON.parseObject(result), BingImageResponse.class);
        bingImageResponse.getImages().forEach(imageInfo -> {
            DataWallPage dataWallPage = dataWallPageMapper.selectOneByStartDate(imageInfo.getStartdate());
            if (dataWallPage == null) {
                DataWallPage wallPageInfo = new DataWallPage();
                wallPageInfo.setStartDate(imageInfo.getStartdate());
                wallPageInfo.setEndDate(imageInfo.getEnddate());
                wallPageInfo.setFullStartDate(imageInfo.getFullstartdate());
                wallPageInfo.setUrl(imageInfo.getUrl());
                wallPageInfo.setCopyright(imageInfo.getCopyright());
                wallPageInfo.setCopyrightLink(imageInfo.getCopyrightlink());
                wallPageInfo.setTitle(imageInfo.getTitle());
                wallPageInfo.setHash(imageInfo.getHsh());
                dataWallPageMapper.insert(wallPageInfo);
            }
        });
        return JSONObject.toJavaObject(JSON.parseObject(result), BingImageResponse.class);
    }
}
