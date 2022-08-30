package com.tangerinespecter.oms;

import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.tangerinespecter.oms.common.config.CosConfig;
import com.tangerinespecter.oms.common.utils.CosClient;
import com.tangerinespecter.oms.system.domain.pojo.FileInfoBean;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CosDemoTest {

    @Resource
    private CosClient cosClient;

    @Test
    public void test() throws FileNotFoundException {
        File file = new File("/Users/zhouliangjun/Documents/WX20220805-234137@2x.png");
        FileInfoBean fileInfoBean = cosClient.uploadImage(new FileInputStream(file), CosConfig.AVATAR_ZONE);
        System.out.println(JSON.toJSONString(fileInfoBean));
        cosClient.delete(CosConfig.AVATAR_ZONE, fileInfoBean.getName());
    }

    public static void main(String[] args) {
        System.out.println(IdUtil.simpleUUID());
    }
}
