package com.tangerineSpecter.oms.system.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 收藏表
 * 
 * @author TangerineSpecter
 * @Date 2019年1月22日
 * @version v0.1.2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkCollection {

	private Long id;

	private String title;

	private String content;

	private String remark;

	private Integer type;

	private Integer sort;

	private Integer isDel;

}