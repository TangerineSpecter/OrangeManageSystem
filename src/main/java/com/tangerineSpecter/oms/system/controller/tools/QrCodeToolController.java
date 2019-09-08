package com.tangerinespecter.oms.system.controller.tools;

import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.listener.LoggerInfo;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.QrCodeInfoVo;
import com.tangerinespecter.oms.system.service.tools.QrCodeToolService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 二维码生成工具
 *
 * @author liangjun.zhou
 * @date 2019年09月08日00:01:21
 */
@Controller
@RequestMapping("/tools/qr-code")
public class QrCodeToolController {

    @Resource
    private QrCodeToolService qrCodeToolService;

    @RequestMapping("/page")
    public String pageInfo() {
        return "tools/qrCode";
    }

    /**
     * 生成二维码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/create")
    @LoggerInfo(value = "创建二维码", event = LogOperation.EVENT_ADD)
    public ServiceResult createQrCode(@Valid QrCodeInfoVo vo) {
        return qrCodeToolService.createQrCode(vo);
    }
}
