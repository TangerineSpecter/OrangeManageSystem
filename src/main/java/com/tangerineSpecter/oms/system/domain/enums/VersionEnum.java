package com.tangerinespecter.oms.system.domain.enums;

import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 版本记录枚举
 *
 * @author TangerineSpecter
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum VersionEnum implements IBaseDbEnum {

    ADD(0, "新增"),
    PERF(1, "优化"),
    MODIFY(2, "改善"),
    FIX(3, "修复"),
    REFACTOR(4, "重构"),
    UNKNOWN(-1, "未知");

    private Integer value;

    private String desc;

    public static Integer convert2Type(String desc) {
        Integer result = UNKNOWN.value;
        for (VersionEnum versionEnum : VersionEnum.values()) {
            if (Objects.equals(versionEnum.getDesc(), desc)) {
                return versionEnum.value;
            }
        }
        return UNKNOWN.value;
    }
}
