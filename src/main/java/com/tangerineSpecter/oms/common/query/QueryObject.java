package com.tangerinespecter.oms.common.query;

import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页查询
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月9日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryObject<T> {

    /**
     * 搜索参数
     */
    private String searchParams;
    /**
     * 当前管理员ID
     */
    private Long adminId = SystemUtils.getSystemUserId();
    /**
     * 默认第一页
     */
    private Integer page = 1;
    /**
     * 默认一页十条
     */
    private Integer limit = 10;
    
    /**
     * 参数
     */
    private T param;
    
}
