package com.tangerinespecter.oms.system.service.tools;

import cn.hutool.core.util.IdUtil;
import cn.hutool.extra.qrcode.QrCodeUtil;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.QrCodeInfoVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.io.File;

@Service
public class QrCodeToolService {

    @Value("${qrcode.path}")
    private String QrCodePath;
    @Value("${qrcode.view.path}")
    private String QrViewPath;
    /**
     * 二维码文件名
     */
    private String QrFileName = "/qrcode.jpg";

    public ServiceResult createQrCode(QrCodeInfoVo vo) {
        String uuid = IdUtil.randomUUID();
        String qrPath = QrCodePath + uuid + QrFileName;
        File dir = new File(qrPath);
        if (!dir.exists()) {
            dir.getParentFile().mkdirs();
        }
        QrCodeUtil.generate(vo.getContent(), vo.getWidth(), vo.getHeight(), new File(qrPath));
        String viewPath = QrViewPath + uuid + QrFileName;
        return ServiceResult.success(viewPath);
    }

}
