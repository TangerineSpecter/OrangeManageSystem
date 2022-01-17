package com.tangerinespecter.oms.common.query;

import com.tangerinespecter.oms.common.utils.SystemUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 卡片笔记页面高级查询
 *
 * @author TangerineSpecter
 * @version 0.4.1
 * @date 2022年01月17日11:28:15
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCardNoteQueryObject extends QueryObject {
	
	/**
	 * 关键词
	 */
	private String keyword;
	/**
	 * 默认当前管理员
	 */
	private Long adminId = SystemUtils.getSystemUserId();
}
