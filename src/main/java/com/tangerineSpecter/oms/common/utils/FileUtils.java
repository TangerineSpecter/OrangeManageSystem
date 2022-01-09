package com.tangerinespecter.oms.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tangerinespecter.oms.common.config.QiNiuConfig;
import lombok.extern.slf4j.Slf4j;

/**
 * 文件工具类
 * @author 丢失的橘子
 */
@Slf4j
public class FileUtils {

    /**
     * 文件上传
     *
     * @return
     */
    public static String uploadFile(byte[] fileBytes) {
        Configuration cfg = new Configuration(Region.region2());
        UploadManager uploadManager = new UploadManager(cfg);
        String accessKey = QiNiuConfig.QI_NIU_ACCESS_KEY;
        String secretKey = QiNiuConfig.QI_NIU_SECRET_KEY;
        String bucket = QiNiuConfig.AVATAR_ZONE;
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);

        try {
            Response response = uploadManager.put(fileBytes, null, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            log.error(r.toString());
        }
        return null;
    }
}
