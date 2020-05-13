package com.tangerinespecter.oms.system.service.system.impl;

import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.FileUtils;
import com.tangerinespecter.oms.system.service.system.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class ResourceServiceImpl implements IResourceService {

    @Override
    public ServiceResult uploadImage(MultipartFile file) {
        if (file == null) {
            return ServiceResult.error(RetCode.FILE_NOT_EXIST);
        }
        try {
            String fileUrl = FileUtils.uploadFile(file.getBytes());
            return ServiceResult.success(fileUrl);
        } catch (Exception e) {
            log.error("文件上传异常:{}", e.getMessage());
            return ServiceResult.error(RetCode.FILE_UPLOAD_EXCEPTION);
        }
    }
}
