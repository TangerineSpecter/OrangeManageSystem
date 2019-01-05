package com.tangerineSpecter.oms.system.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统信息
 * 
 * @author TangerineSpecter
 * @DateTime 2019年1月5日 14:55:39
 * @version v0.0.3
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemInfoBean {

	/** 操作系统 */
	private String osName;
	/** 已使用内存 */
	private long usedMemory;
	/** 剩余内存 */
	private long freeMemory;
	/** 总内存 */
	private long totalMemory;
	/** 总线程数 */
	private int totalThread;
	/** cpu使用率 */
	private int cpuRatio;
	/** 内存使用率 */
	private int memoryRatio;
	/** 硬盘使用率 */
	private int diskRatio;
}
