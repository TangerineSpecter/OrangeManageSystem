package com.tangerinespecter.oms.system.controller.system;

import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.FileUtils;
import com.tangerinespecter.oms.system.service.system.IResourceService;
import org.apache.ibatis.annotations.Param;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源控制
 *
 * @author TangerineSpecter
 * @version 0.3.1
 * @date 2020年05月13日14:03:19
 */
@RestController
@RequestMapping("/resource")
public class ResourceController {

    @Resource
    private IResourceService resourceService;

    /**
     * 上传图片
     *
     * @param file 图片资源
     * @return 图片地址
     */
    @RequestMapping("/upload-image")
    public ServiceResult uploadImage(@Param("file") MultipartFile file) {
        return resourceService.uploadImage(file);
    }
}
