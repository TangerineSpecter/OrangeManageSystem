package com.tangerinespecter.oms.common.utils;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.IdUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import com.tangerinespecter.oms.common.config.CosConfig;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import com.tangerinespecter.oms.system.domain.pojo.FileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * cos资源工具类
 *
 * @author 丢失的橘子
 * @date 2022年08月30日11:31:27
 */
@Slf4j
@Service
public class CosClient {

    @Resource
    private CosConfig cosConfig;

    /**
     * 组装头像地址
     *
     * @param systemUser 头像
     * @return 头像地址
     */
    public void initAvatar(SystemUser systemUser) {
        //头像地址为空，或者包含完整地址则不处理
        if (CharSequenceUtil.isEmpty(systemUser.getAvatar()) || CharSequenceUtil.contains(systemUser.getAvatar(), cosConfig.getBucketPath())) {
            return;
        }
        systemUser.setAvatar(cosConfig.getBucketPath() + CosConfig.AVATAR_ZONE + systemUser.getAvatar());
    }

    /**
     * 创建cos连接
     *
     * @return cos连接对象
     */
    public COSClient createClient() {
        BasicCOSCredentials cred = new BasicCOSCredentials(cosConfig.getSecretId(), cosConfig.getSecretKey());
        Region region = new Region(cosConfig.getBucketRegion());
        ClientConfig clientConfig = new ClientConfig(region);
        return new COSClient(cred, clientConfig);
    }

    /**
     * 获取存储桶列表
     *
     * @return 存储桶列表
     */
    public List<Bucket> getBuckets() {
        COSClient cosClient = this.createClient();
        try {
            return cosClient.listBuckets();
        } catch (Exception e) {
            log.error("query bucket list error, exception: {}. " + e, e.getMessage());
            throw new BusinessException(RetCode.FAIL);
        } finally {
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }
    }

    /**
     * 上传文件
     *
     * @param file     文件
     * @param path     路径
     * @param fileName 文件名，需包含后缀
     * @return 上传结果
     */
    public PutObjectResult uploadFile(File file, String path, String fileName) {
        COSClient cosClient = this.createClient();
        try {
            String key = CharSequenceUtil.format("{}{}", path, fileName);
            PutObjectRequest request = new PutObjectRequest(cosConfig.getBucketName(), key, file);
            return cosClient.putObject(request);
        } catch (Exception e) {
            log.error("upload file error, exception: {}. " + e, e.getMessage());
            throw new BusinessException(RetCode.FAIL);
        } finally {
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }
    }

    /**
     * 上传图片文件
     *
     * @param inputStream 文件流
     * @param path        路径
     * @return 文件信息
     */
    public FileInfoBean uploadImage(InputStream inputStream, String path) {
        COSClient cosClient = this.createClient();
        try {
            String fileName = IdUtil.simpleUUID() + ".jpg";
            String key = CharSequenceUtil.format("{}{}", path, fileName);
            // 设置
            ObjectMetadata objectMetadata = new ObjectMetadata();
            // 设置输入流长度
            objectMetadata.setContentLength(inputStream.available());
            // 设置 Content type, 默认是 application/octet-stream
            objectMetadata.setContentType("image/jpeg");
            PutObjectRequest request = new PutObjectRequest(cosConfig.getBucketName(), key, inputStream, objectMetadata);
            cosClient.putObject(request);
            return new FileInfoBean(fileName, Convert.toLong(inputStream.available()), cosConfig.getBucketPath() + CosConfig.AVATAR_ZONE + fileName);
        } catch (Exception e) {
            log.error("upload image error, exception: {}. " + e, e.getMessage());
            throw new BusinessException(RetCode.FAIL);
        } finally {
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }
    }

    /**
     * 删除文件
     *
     * @param path     路径
     * @param fileName 文件名，需包含后缀
     */
    public void delete(String path, String fileName) {
        COSClient cosClient = this.createClient();
        try {
            String key = CharSequenceUtil.format("{}{}", path, fileName);
            DeleteObjectRequest request = new DeleteObjectRequest(cosConfig.getBucketName(), key);
            cosClient.deleteObject(request);
        } catch (Exception e) {
            log.error("upload file error, exception: {}. " + e, e.getMessage());
            throw new BusinessException(RetCode.FAIL);
        } finally {
            // 关闭客户端(关闭后台线程)
            cosClient.shutdown();
        }
    }
}
