package com.tangerinespecter.oms.common.query;

import com.tangerinespecter.oms.common.context.UserContext;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 基础查询参数
 *
 * @author 丢失的橘子
 * @version v0.5.1
 * @date 2022年10月26日18:20:16
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseQueryObject implements Serializable {

    /**
     * 当前管理员ID
     */
    @ApiModelProperty(value = "操作管理员uid", hidden = true)
    private String uid = UserContext.getUid();
}
