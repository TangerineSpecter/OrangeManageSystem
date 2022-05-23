package com.tangerinespecter.oms.system.domain.enums;

import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 健康管理记录类型
 *
 * @author Tangerinespecter
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum HealthRecordTypeEnum implements IBaseDbEnum {

    TODAY_RECORD_TYPE(0, "今天的记录"),
    YESTERDAY_RECORD_TYPE(1, "昨天的记录");

    private Integer value;

    private String desc;

    /**
     * 根据类型获取时间
     *
     * @param type 记录类型
     * @return 时间字符 yyyy-MM-dd
     */
    public static String getDate(Integer type) {
        if (TODAY_RECORD_TYPE.value.equals(type)) {
            return DateUtil.today();
        } else if (YESTERDAY_RECORD_TYPE.value.equals(type)) {
            return DateUtil.yesterday().toDateStr();
        }
        return null;
    }

}
