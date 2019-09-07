package com.tangerinespecter.oms.system.service.tools;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.QrCodeInfoVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class QrCodeToolService {

    @Value("${qrcode.path}")
    private String QrCodePath;

    public ServiceResult createQrCode(QrCodeInfoVo vo) {
        String uuid = IdUtil.randomUUID();
        String qrPath = QrCodePath + uuid + "/qrcode.jpg";
        File dir = new File(qrPath);
        if (!dir.exists()) {
            dir.getParentFile().mkdirs();
        }
        QrCodeUtil.generate(vo.getContent(), vo.getWidth(), vo.getHeight(), new File(qrPath));
        return ServiceResult.success(qrPath);
    }

}
