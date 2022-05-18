package com.tangerinespecter.oms.common.query;

import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.SystemUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页查询
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @date 2019年1月9日
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryObject<T> implements Serializable{

    /**
     * 搜索参数
     */
    private T searchParams;
    /**
     * 当前管理员ID
     */
    private String uid = SystemUtils.getSystemUserId();
    /**
     * 默认第一页
     */
    private Integer page = 1;
    /**
     * 默认一页十条
     */
    private Integer limit = 10;

}
