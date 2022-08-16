package com.tangerinespecter.oms.system.domain.enums;

import com.tangerinespecter.oms.common.enums.IBaseDbEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 星座命名枚举
 *
 * @author TangerineSpecter
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
public enum StarNameEnum implements IBaseDbEnum {

    ARIES(1, "Aries", "白羊座"),
    TAURUS(2, "Taurus", "金牛座"),
    GEMINI(3, "Gemini", "双子座"),
    CANCER(4, "Cancer", "巨蟹座"),
    LEO(5, "Leo", "狮子座"),
    VIRGO(6, "Virgo", "处女座"),
    LIBRA(7, "Libra", "天秤座"),
    SCORPIO(8, "Scorpio", "天蝎座"),
    SAGITTARIUS(9, "Sagittarius", "射手座"),
    CAPRICORN(10, "Capricorn", "摩羯座"),
    AQUARIUS(11, "Aquarius", "水瓶座"),
    PISCES(12, "Pisces", "双鱼座");

    private Integer value;

    private String name;

    private String desc;

    /**
     * 中文名 -> 英文名
     *
     * @param star 中文名
     * @return 英文名
     */
    public static String chnName2EnName(String star) {
        for (StarNameEnum starNameEnum : StarNameEnum.values()) {
            if (Objects.equals(starNameEnum.getDesc(), star)) {
                return starNameEnum.getName();
            }
        }
        return null;
    }

}
