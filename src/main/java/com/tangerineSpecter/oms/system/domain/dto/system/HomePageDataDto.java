package com.tangerinespecter.oms.system.domain.dto.system;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HomePageDataDto {

    /**
     * 页面缓存清理地址
     */
    private ClearInfo clearInfo;
    /**
     * 主页信息
     */
    private HomeInfo homeInfo;
    /**
     * 系统主题信息
     */
    private LogoInfo logoInfo;
    /**
     * 菜单信息
     */
    private Map<String, MenuChildInfo> menuInfo;


    public LogoInfo getLogoInfo() {
        return new LogoInfo();
    }

    public ClearInfo getClearInfo() {
        return new ClearInfo();
    }

    public HomeInfo getHomeInfo() {
        return new HomeInfo();
    }
}
