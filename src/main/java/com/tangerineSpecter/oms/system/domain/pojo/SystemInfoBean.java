package com.tangerinespecter.oms.system.domain.pojo;

import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 系统信息
 *
 * @author TangerineSpecter
 * @version v0.0.3
 * @DateTime 2019年1月5日 14:55:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SystemInfoBean {

    /**
     * 操作系统
     */
    private String osName;
    /**
     * 已使用内存
     */
    private long usedMemory;
    /**
     * 剩余内存
     */
    private long freeMemory;
    /**
     * 总内存
     */
    private long totalMemory;
    /**
     * 总线程数
     */
    private int totalThread;
    /**
     * cpu使用率
     */
    private int cpuRatio;
    /**
     * 内存使用率
     */
    private int memoryRatio;
    /**
     * 硬盘使用率
     */
    private int diskRatio;
    /**
     * 系统标题
     */
    private String systemTitle;
    /**
     * 系统版本
     */
    private String version;
    /**
     * 星座名称
     */
    private String starName;
    /**
     * 综合指数
     */
    private Integer allLuck;
    /**
     * 工作指数
     */
    private Integer workLuck;
    /**
     * 健康指数
     */
    private Integer health;
    /**
     * 爱情指数
     */
    private Integer love;
    /**
     * 财运指数
     */
    private Integer money;
    /**
     * 今日提示
     */
    private String todayTip;
    /**
     * 首页菜单
     */
    private List<SystemMenu> menus;
}
