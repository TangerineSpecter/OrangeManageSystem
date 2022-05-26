package com.tangerinespecter.oms.job.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;

/**
 * 聚合Api默认响应结果
 *
 * @author TangerineSpecter
 */
@Data
public class JuheApiBaseResponse<T extends Serializable> implements Serializable {

    /**
     * 返回说明
     */
    @JSONField(name = "reason")
    private String reason;
    /**
     * 错误码
     */
    @JSONField(name = "error_code")
    private Integer errorCode;
    /**
     * 返回结果集
     */
    @JSONField(name = "result")
    private T result;

    /**
     * 是否请求成功
     *
     * @return true：成功
     */
    public boolean isSuccess() {
        return this.errorCode == 0;
    }
}
