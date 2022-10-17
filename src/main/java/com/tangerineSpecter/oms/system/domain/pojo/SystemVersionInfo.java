package com.tangerinespecter.oms.system.domain.pojo;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Getter
@Component
@ConfigurationProperties(SystemVersionInfo.PREFIX)
//@PropertySource(value = "classpath:/application-version.yaml")
public class SystemVersionInfo {

    public static final String PREFIX = "version";
    @Value("${system.version}")
    private String version;
    private List<History> history = new ArrayList<>();

    @Data
    public static class History {
        private String code;

        private String date;

        public List<Content> content;

        @Data
        public static class Content {

            private String info;
        }
    }
}
