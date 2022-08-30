package com.tangerinespecter.oms.system.service.system;

import com.tangerinespecter.oms.system.domain.pojo.FileInfoBean;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IResourceService {

    /**
     * 上传图片
     *
     * @param file 图片资源
     * @return 图片地址
     */
    FileInfoBean uploadImage(MultipartFile file);

    String uploadFile(MultipartFile file, String fileName) throws IOException;

}
