package com.tangerineSpecter.oms.system.domain.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 管理员个人信息
 * 
 * @author TangerineSpecter
 * @Date 2019年1月16日
 * @version v0.1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManagerInfoBean {
	/** 星座 */
	private String starName;
	/** 综合指数 */
	private String allLuck;
	/** 健康指数 */
	private String health;
	/** 爱情指数 */
	private String love;
	/** 财运指数 */
	private String money;
	/** 工作指数 */
	private String workLuck;
}
