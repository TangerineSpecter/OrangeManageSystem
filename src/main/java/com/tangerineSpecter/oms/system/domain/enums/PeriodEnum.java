package com.tangerinespecter.oms.system.domain.enums;

import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 周期枚举类型
 *
 * @author Tangerinespecter
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum PeriodEnum implements IBaseDbEnum {

    DAILY(1,"每天"),
    MONTHLY(2,"每月"),
    YEARLY(3,"每年");


    private Integer value;

    private String desc;
}
