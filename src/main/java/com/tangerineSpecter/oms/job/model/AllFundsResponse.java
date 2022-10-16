package com.tangerinespecter.oms.job.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 所有基金查询响应结果
 *
 * @author TangerineSpecter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
@ToString(callSuper = true)
public class AllFundsResponse implements Serializable {

    @JSONField(name = "code")
    private Integer code;
    @JSONField(name = "message")
    private String message;
    /**
     * 样例：
     * "000001",
     * "HXCZHH",
     * "华夏成长混合",
     * "混合型-灵活",
     * "HUAXIACHENGZHANGHUNHE"
     */
    @JSONField(name = "data")
    private List<String[]> data;
}



