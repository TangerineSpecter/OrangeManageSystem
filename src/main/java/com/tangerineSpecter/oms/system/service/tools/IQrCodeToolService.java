package com.tangerinespecter.oms.system.service.tools;

import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.QrCodeInfoVo;

public interface IQrCodeToolService {

    ServiceResult createQrCode(QrCodeInfoVo vo);
}
