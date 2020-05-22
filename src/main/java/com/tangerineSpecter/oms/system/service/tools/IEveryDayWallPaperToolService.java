package com.tangerinespecter.oms.system.service.tools;

import com.tangerinespecter.oms.system.domain.pojo.BingImageResponse;
import com.tangerinespecter.oms.system.domain.vo.tools.BingImageReq;

public interface IEveryDayWallPaperToolService {

    /**
     * 获取壁纸信息
     *
     * @return 壁纸信息
     */
    BingImageResponse wallPagerInfo(BingImageReq req);

}
