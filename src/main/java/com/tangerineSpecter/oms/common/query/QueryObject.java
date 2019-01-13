package com.tangerineSpecter.oms.common.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询
 * 
 * @author TangerineSpecter
 * @Date 2019年1月9日
 * @version v0.0.5
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryObject {
	/** 默认第一页 */
	private Integer page = 1;
	/** 默认一页十条 */
	private Integer size = 10;

	public Integer getStart() {
		this.page = (this.page > 0 ? this.page : 1);
		return (this.page - 1) * this.size;
	}
}
