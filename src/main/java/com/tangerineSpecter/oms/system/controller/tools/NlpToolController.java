package com.tangerinespecter.oms.system.controller.tools;

import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.tools.NlpInfoVo;
import com.tangerinespecter.oms.system.domain.vo.tools.QrCodeInfoVo;
import com.tangerinespecter.oms.system.service.tools.INlpToolService;
import com.tangerinespecter.oms.system.service.tools.IQrCodeToolService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

/**
 * NLP语言分析工具
 *
 * @author 丢失的橘子
 * @date 2023年03月02日10:13:57
 */
@RestController
@ReWriteBody
@RequiredArgsConstructor
@Api(tags = "NLP语言分析工具接口")
@RequestMapping("/tools/nlp")
public class NlpToolController {

    private final INlpToolService nlpToolService;

    @GetMapping("/page")
    @ApiOperation(value = "NLP语言分析页面跳转")
    @RequiresPermissions("tools:nlp:page")
    public ModelAndView pageInfo() {
        return ServiceResult.jumpPage("tools/nlp");
    }

    /**
     * nlp语言分析
     */
    @PostMapping("/analysis")
    @ApiOperation("语言分析")
    @LoggerInfo(value = "分析语言", event = LogOperation.EVENT_USE)
    public NlpInfoVo analysis(@ApiParam("分析内容文件，内容/文件至少有其一") @RequestParam(value = "file", required = false) MultipartFile file,
                              @ApiParam("分析内容") @RequestParam(value = "content", required = false) String content) {
        return nlpToolService.analysis(file, content);
    }
}
