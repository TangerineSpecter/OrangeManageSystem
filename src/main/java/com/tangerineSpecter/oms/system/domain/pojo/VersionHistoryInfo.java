package com.tangerinespecter.oms.system.domain.pojo;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "history")
public class VersionHistoryInfo {

    private String code;
    private Integer num;
}
