package com.tangerinespecter.oms.system.service.tools.impl;

import cn.hutool.extra.qrcode.QrCodeUtil;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.QrCodeInfoVo;
import com.tangerinespecter.oms.system.service.tools.IQrCodeToolService;
import org.springframework.stereotype.Service;

@Service
public class QrCodeToolServiceImpl implements IQrCodeToolService {

	@Override
	public ServiceResult createQrCode(QrCodeInfoVo vo) {
		return ServiceResult.success(QrCodeUtil.generatePng(vo.getContent(), vo.getWidth(), vo.getHeight()));
	}

}
