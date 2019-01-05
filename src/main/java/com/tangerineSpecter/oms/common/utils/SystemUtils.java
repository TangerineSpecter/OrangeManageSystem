package com.tangerineSpecter.oms.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import lombok.extern.slf4j.Slf4j;

/**
 * 系统工具类
 * 
 * @author TangerineSpecter
 * @DateTime 2019年1月5日 14:37:26
 * @version v0.0.3
 */
@Slf4j
public class SystemUtils {

	/** Linux CPU信息文件路径 */
	private static final String LINUX_CPU_INFO_FILE = "/proc/stat";
	/** Linux 内存信息文件路径 */
	private static final String LINUX_MEMORY_INFO_FILE = "/proc/meminfo";

	/**
	 * 获取操作系统
	 */
	public static String getOsName() {
		String osName = System.getProperty("os.name");
		return osName;
	}

	/**
	 * 获取linux Cpu使用率
	 */
	public static double getCpuUsageInfo() {
		double cpuRatio = 0;
		try {
			File file = new File(LINUX_CPU_INFO_FILE);
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			StringTokenizer token = new StringTokenizer(br.readLine());
			token.nextToken();

			long user1 = Long.parseLong(token.nextToken() + "");
			long nice1 = Long.parseLong(token.nextToken() + "");
			long sys1 = Long.parseLong(token.nextToken() + "");
			long idle1 = Long.parseLong(token.nextToken() + "");
			Thread.sleep(1000);

			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			token = new StringTokenizer(br.readLine());
			token.nextToken();

			long user2 = Long.parseLong(token.nextToken());
			long nice2 = Long.parseLong(token.nextToken());
			long sys2 = Long.parseLong(token.nextToken());
			long idle2 = Long.parseLong(token.nextToken());
			cpuRatio = (double) ((user2 + sys2 + nice2) - (user1 + sys1 + nice1)) * 100
					/ (double) ((user2 + nice2 + sys2 + idle2) - (user1 + nice1 + sys1 + idle1));
		} catch (Exception e) {
			log.error("cpu信息获取异常：{}", e);
		}
		return Math.round(cpuRatio);
	}

	/**
	 * 获取内存使用率
	 */
	public static double getMemoryUsageInfo() {
		Map<String, Object> map = new HashMap<String, Object>();
		InputStreamReader inputs = null;
		BufferedReader buffer = null;
		try {
			inputs = new InputStreamReader(new FileInputStream(LINUX_MEMORY_INFO_FILE));
			buffer = new BufferedReader(inputs);
			String line = "";
			while (true) {
				line = buffer.readLine();
				if (line == null)
					break;
				int beginIndex = 0;
				int endIndex = line.indexOf(":");
				if (endIndex != -1) {
					String key = line.substring(beginIndex, endIndex);
					beginIndex = endIndex + 1;
					endIndex = line.length();
					String memory = line.substring(beginIndex, endIndex);
					String value = memory.replace("kB", "").trim();
					map.put(key, value);
				}
			}
			long memTotal = Long.parseLong(map.get("MemTotal").toString());
			long memFree = Long.parseLong(map.get("MemFree").toString());
			long memused = memTotal - memFree;
			long buffers = Long.parseLong(map.get("Buffers").toString());
			long cached = Long.parseLong(map.get("Cached").toString());
			double usage = (double) (memused - buffers - cached) / memTotal * 100;
			BigDecimal b1 = new BigDecimal(usage);
			double memoryUsage = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			return Math.round(memoryUsage);
		} catch (Exception e) {
			log.error("获取内存信息失败：{}", e);
		} finally {
			try {
				buffer.close();
				inputs.close();
			} catch (Exception e1) {
				log.error("流关闭失败：{}", e1);
			}
		}
		return 0;
	}

	/**
	 * Linux 得到磁盘的使用率
	 */
	public static double getDiskUsageInfo() throws Exception {
		double totalHD = 0;
		double usedHD = 0;
		Runtime rt = Runtime.getRuntime();
		// df -hl 查看硬盘空间
		Process p = rt.exec("df -hl");
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String str = null;
			String[] strArray = null;
			while ((str = in.readLine()) != null) {
				int m = 0;
				strArray = str.split(" ");
				for (String tmp : strArray) {
					if (tmp.trim().length() == 0)
						continue;
					++m;
					if (tmp.indexOf("G") != -1) {
						if (m == 2) {
							if (!tmp.equals("") && !tmp.equals("0"))
								totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
						}
						if (m == 3) {
							if (!tmp.equals("none") && !tmp.equals("0"))
								usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1)) * 1024;
						}
					}
					if (tmp.indexOf("M") != -1) {
						if (m == 2) {
							if (!tmp.equals("") && !tmp.equals("0"))
								totalHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
						}
						if (m == 3) {
							if (!tmp.equals("none") && !tmp.equals("0"))
								usedHD += Double.parseDouble(tmp.substring(0, tmp.length() - 1));
						}
					}
				}
			}
		} catch (Exception e) {
			log.debug("硬盘信息获取异常：{}", e);
		} finally {
			in.close();
		}
		// 保留2位小数
		double precent = (usedHD / totalHD) * 100;
		BigDecimal b1 = new BigDecimal(precent);
		precent = b1.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return Math.round(precent);
	}
}
