package com.tangerinespecter.oms.system.domain.vo.data;

import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("食物信息模型")
public class FoodLibraryInfoVo implements Serializable {

    @NotNull(groups = {Update.class}, message = "更新时，id不能为空")
    @ApiModelProperty("交易数据id")
    private Long id;
    @NotNull(message = "名称不能为空")
    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("图片地址")
    private String logo;

    @ApiModelProperty("卡路里/100g")
    private BigDecimal calories;

    @ApiModelProperty("蛋白质/100g")
    private BigDecimal protein;

    @ApiModelProperty("脂肪/100g")
    private BigDecimal fat;

    @ApiModelProperty("碳水化合物/100g")
    private BigDecimal carbs;

    @ApiModelProperty("膳食纤维/100g")
    private BigDecimal dietaryFiber;
}
