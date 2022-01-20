package com.tangerinespecter.oms.system.service.tools;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.VideoWatermarkInfoVo;

import java.io.IOException;

public interface IVideoWaterMarkToolService {
	
	ServiceResult clearVideoWatermark(VideoWatermarkInfoVo vo) throws Exception;
}
