package com.tangerinespecter.oms.system.service.tools.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.QrCodeInfoVo;
import com.tangerinespecter.oms.system.service.tools.IQrCodeToolService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class QrCodeToolServiceImpl implements IQrCodeToolService {

    @Value("${qrcode.path}")
    private String QrCodePath;
    @Value("${qrcode.view.path}")
    private String QrViewPath;
    /**
     * 二维码文件名
     */
    private String QrFileName = "/qrcode.jpg";

    @Override
    public ServiceResult createQrCode(QrCodeInfoVo vo) {
        String uuid = IdUtil.randomUUID();
        String qrPath = DateUtil.today() + "/" + uuid + QrFileName;
        String savePath = QrCodePath + qrPath;
        File dir = new File(savePath);
        if (!dir.exists()) {
            dir.getParentFile().mkdirs();
        }
        QrCodeUtil.generate(vo.getContent(), vo.getWidth(), vo.getHeight(), new File(savePath));
        String viewPath = QrViewPath + qrPath;
        return ServiceResult.success(viewPath);
    }

}
