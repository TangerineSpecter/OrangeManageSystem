package com.tangerinespecter.oms.system.domain.enums;

import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 用户状态枚举
 *
 * @author Tangerinespecter
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum UserStatusEnum implements IBaseDbEnum {
    FREEZE(0, "冻结"),
    EFFECTIVE(1, "可用");

    private Integer value;

    private String desc;

}
