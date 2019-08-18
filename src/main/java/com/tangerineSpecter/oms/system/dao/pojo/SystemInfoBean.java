package com.tangerinespecter.oms.system.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
}
