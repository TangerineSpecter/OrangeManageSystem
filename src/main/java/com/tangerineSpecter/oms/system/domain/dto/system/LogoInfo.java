package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.common.constants.SystemConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogoInfo {

    /**
     * 系统标题
     */
    private String title = SystemConstant.SYSTEM_CONFIG.getHomeTitle();
    /**
     * 系统logo
     */
    private String image = "layui/images/logo.png";
    /**
     * 跳转地址
     */
    private String href = "/";
    /**
     * 跳转方式
     */
    private String target = "_self";
}
