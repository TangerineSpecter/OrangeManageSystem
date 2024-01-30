package com.tangerinespecter.oms.job.service;

import com.tangerinespecter.oms.system.domain.entity.DataWallPage;
import com.tangerinespecter.oms.system.domain.pojo.BingImageResponse;
import com.tangerinespecter.oms.system.domain.vo.tools.BingImageReq;
import com.tangerinespecter.oms.system.mapper.DataWallPageMapper;
import com.tangerinespecter.oms.system.service.tools.IEveryDayWallPaperToolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class WallPageQuartzService {

    private final IEveryDayWallPaperToolService everyDayWallPaperToolService;
    private final DataWallPageMapper dataWallPageMapper;

    public void runData() {
        log.info("[执行必应每日数据写入任务]");
        BingImageResponse bingImageResponse = everyDayWallPaperToolService.wallPagerInfo(new BingImageReq());
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
                log.info("进行每日壁纸数据插入，插入壁纸为：[{}]", imageInfo.getCopyright());
            }
        });
        log.info("[每日壁纸定时任务执行完毕]");
    }
}
