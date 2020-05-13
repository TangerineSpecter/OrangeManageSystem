package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

public interface IResourceService {

    ServiceResult uploadImage(MultipartFile file);
}
