package com.tangerinespecter.oms.system.domain.dto.system;

import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("令牌模型")
public class SystemTokenVo implements Serializable {

    @ApiModelProperty("数据id，更新必传")
    @NotNull(message = "id不能为空", groups = {Update.class})
    private Long id;
    @ApiModelProperty("名称")
    @NotBlank(message = "名称不能为空")
    private String name;
    @ApiModelProperty("令牌")
    @NotNull(message = "令牌不能为空")
    private String token;
    @ApiModelProperty("类型，0：机器人")
    @NotNull(message = "类型不能为空")
    private Integer type;

}
