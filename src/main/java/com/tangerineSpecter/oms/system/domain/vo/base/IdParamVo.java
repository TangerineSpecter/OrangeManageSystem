package com.tangerinespecter.oms.system.domain.vo.base;

import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IdParamVo implements Serializable {

    @ApiModelProperty("数据id，更新必传")
    @NotNull(message = "id不能为空", groups = {Update.class})
    private Long id;
}
