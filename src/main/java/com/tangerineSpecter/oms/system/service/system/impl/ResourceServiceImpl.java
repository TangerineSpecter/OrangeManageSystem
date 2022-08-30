package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.lang.Assert;
import com.tangerinespecter.oms.common.config.CosConfig;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.utils.CosClient;
import com.tangerinespecter.oms.common.utils.FileUtils;
import com.tangerinespecter.oms.system.domain.pojo.FileInfoBean;
import com.tangerinespecter.oms.system.service.system.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

@Slf4j
@Service
public class ResourceServiceImpl implements IResourceService {

    @Resource
    private CosClient cosClient;

    @Override
    public FileInfoBean uploadImage(MultipartFile file) {
        Assert.isTrue(file != null, () -> new BusinessException(RetCode.FILE_NOT_EXIST));
        try {
            return cosClient.uploadImage(file.getInputStream(), CosConfig.AVATAR_ZONE);
        } catch (Exception e) {
            log.error("文件上传异常:{}", e.getMessage());
            throw new BusinessException(RetCode.FILE_UPLOAD_EXCEPTION);
        }
    }

    @Override
    public String uploadFile(MultipartFile file, String fileName) throws IOException {
        return FileUtils.uploadFile(file.getBytes());
    }
}
