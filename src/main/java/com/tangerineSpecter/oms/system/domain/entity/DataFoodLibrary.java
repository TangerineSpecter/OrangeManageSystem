package com.tangerinespecter.oms.system.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 食物资料库
 * </p>
 *
 * @author 丢失的橘子
 * @since 2022-10-26
 */
@Data
@Alias("DataExchangeRate")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("data_food_library")
@ApiModel(value = "DataFoodLibrary对象", description = "食物资料库")
public class DataFoodLibrary extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

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

    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;

}
