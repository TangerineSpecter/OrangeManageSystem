package com.tangerinespecter.oms.system.dao.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 首页数据
 *
 * @author TangerineSpecter
 * @version v0.0.4
 * @Date 2019年1月6日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IndexDataBean {

    /**
     * 系统信息
     */
    private SystemInfoBean systemInfo;
}
