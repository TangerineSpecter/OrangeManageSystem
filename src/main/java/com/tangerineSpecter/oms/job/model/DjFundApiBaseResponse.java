package com.tangerinespecter.oms.job.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * DJ基金基础响应结果
 *
 * @author TangerineSpecter
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuppressWarnings("all")
@ToString(callSuper = true)
public class DjFundApiBaseResponse<T extends Serializable> implements Serializable {

    @JSONField(name = "result_code")
    private Integer resultCode;
    @JSONField(name = "data")
    private T data;

}



