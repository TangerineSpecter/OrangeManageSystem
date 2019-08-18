package com.tangerinespecter.oms.system.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 管理员个人信息
 *
 * @author TangerineSpecter
 * @version v0.1.0
 * @Date 2019年1月16日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ManagerInfoBean {
    /**
     * 星座
     */
    private String starName;
    /**
     * 综合指数
     */
    private String allLuck;
    /**
     * 健康指数
     */
    private String health;
    /**
     * 爱情指数
     */
    private String love;
    /**
     * 财运指数
     */
    private String money;
    /**
     * 工作指数
     */
    private String workLuck;
    /**
     * 公告
     */
    private String notice;
}
