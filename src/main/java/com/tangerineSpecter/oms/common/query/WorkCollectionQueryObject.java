package com.tangerineSpecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 收藏页面高级查询
 * 
 * @author TangerineSpecter
 * @Date 2019年1月9日
 * @version v0.0.5
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkCollectionQueryObject extends QueryObject {
	/** 类型 */
	private String type;
}
