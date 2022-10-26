package com.tangerinespecter.oms.system.domain.entity;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

/**
 * <p>
 * 基金数据表
 * </p>
 *
 * @author 丢失的橘子
 * @since 2022-10-15
 */
@Data
@Alias("DataExchangeRate")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("data_fund")
@ApiModel(value = "DataFund对象", description = "基金数据表")
public class DataFund extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("基金名称")
    private String name;

    @ApiModelProperty("基金名称（简拼）")
    private String simpleName;

    @ApiModelProperty("基金名称（全称）")
    private String fullName;

    @ApiModelProperty("基金代码")
    private String code;

    @ApiModelProperty("基金类型")
    private String type;

    @ApiModelProperty("删除状态（0：未删除；1：已删除）")
    private Integer isDel;

    /**
     * 响应数据 -> 基金model
     *
     * @param data 数据
     * @return 数据model
     */
    public static DataFund convert2Model(String[] data) {
        DataFund fund = new DataFund();
        fund.setCode(ArrayUtil.get(data, 0));
        fund.setSimpleName(ArrayUtil.get(data, 1));
        fund.setName(ArrayUtil.get(data, 2));
        fund.setType(ArrayUtil.get(data, 3));
        fund.setFullName(ArrayUtil.get(data, 4));
        return fund;
    }
}
