package com.tangerinespecter.oms.job.bean;

import com.tangerinespecter.oms.common.constants.CommonConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 星座接口调用配置
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月7日
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConstellationConfigBean {

    /**
     * 星座名称
     */
    private String consName;
    /**
     * 运势类型（today,tomorrow,week,month,year）
     */
    private String type = "today";
    /**
     * key
     */
    private String key;

}
