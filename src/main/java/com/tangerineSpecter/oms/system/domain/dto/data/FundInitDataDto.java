package com.tangerinespecter.oms.system.domain.dto.data;

import com.tangerinespecter.oms.system.domain.entity.DataFund;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("基金初始化数据结果")
public class FundInitDataDto implements Serializable {

    @ApiModelProperty("新增基金数据")
    private List<DataFund> insertFundData;

    @ApiModelProperty("全部基金数据")
    private List<DataFund> allFundData;
}
