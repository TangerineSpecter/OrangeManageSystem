package com.tangerinespecter.oms;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collections;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class TableGeneratorTest {

    @Value("${spring.datasource.druid.url}")
    private String url;
    @Value("${spring.datasource.druid.username}")
    private String username;
    @Value("${spring.datasource.druid.password}")
    private String password;

    @Test
    public void generator() {
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> {
                    builder.author("丢失的橘子") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("./src/test/java/com/tangerinespecter/oms"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("代码生成") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "./src/test/java/com/tangerinespecter/oms/代码生成")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("system_token") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
