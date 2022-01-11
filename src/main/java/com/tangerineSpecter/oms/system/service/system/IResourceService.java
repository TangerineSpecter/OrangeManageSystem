package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.common.result.ServiceResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IResourceService {

    ServiceResult uploadImage(MultipartFile file);

    String uploadFile(MultipartFile file, String fileName) throws IOException;

}
