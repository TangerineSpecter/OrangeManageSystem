package com.tangerinespecter.oms.system.domain.vo.tools;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 必应请求参数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BingImageReq {

    /**
     * 返回数据格式:
     * js (json格式)
     * xml (xml格式)
     */
    private String format = "js";
    /**
     * 壁纸截止天数 0:当天；1：昨天；以此类推，最多7天前
     */
    private Integer idx = 0;
    /**
     * 返回数量：一次最多8张
     */
    private Integer n = 8;
    /**
     * 地区：默认中国
     */
    private String mkt = "zh-CN";
}
