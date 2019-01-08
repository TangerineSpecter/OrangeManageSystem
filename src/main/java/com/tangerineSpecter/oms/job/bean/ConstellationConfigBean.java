package com.tangerineSpecter.oms.job.bean;

import com.tangerineSpecter.oms.common.constant.CommonConstant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 星座接口调用配置
 * 
 * @author TangerineSpecter
 * @Date 2019年1月7日
 * @version v0.0.5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstellationConfigBean {

	/** 星座名称 */
	private String consName;
	/** 运势类型（today,tomorrow,week,month,year） */
	private String type = "today";
	/** key */
	private String key = CommonConstant.JUHE_API_CONSTELLATION_KEY;

}
