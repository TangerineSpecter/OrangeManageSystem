package com.tangerinespecter.oms.system.service.tools;

import com.tangerinespecter.oms.system.domain.vo.tools.NlpInfoVo;
import org.springframework.web.multipart.MultipartFile;

public interface INlpToolService {

    /**
     * 分析内容
     *
     * @param file    分析文件
     * @param content 分析内容
     */
    NlpInfoVo analysis(MultipartFile file, String content);
}
