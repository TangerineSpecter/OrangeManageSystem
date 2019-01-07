package com.tangerineSpecter.oms.job.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 星座运势表
 * 
 * @author TangerineSpecter
 * @Date 2019年1月7日
 * @version v0.0.5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataConstellation {

	private Long id;
	/** 星座名称 */
	private String name;
	/** 时间（中文） */
	private String dateTime;
	/** 时间（数字） */
	private Integer date;
	/** 综合指数 */
	private String allLuck;
	/** 幸运色 */
	private String color;
	/** 健康指数 */
	private String health;
	/** 爱情指数 */
	private String love;
	/** 财运指数 */
	private String money;
	/** 幸运数字 */
	private Integer number;
	/** 速配星座 */
	private String QFriend;
	/** 今日概述 */
	private String summary;
	/** 工作指数 */
	private String workLuck;
	/** 创建时间 */
	private String createTime;
}
