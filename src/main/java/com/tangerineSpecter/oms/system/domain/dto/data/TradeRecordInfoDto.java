package com.tangerinespecter.oms.system.domain.dto.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("交易数据信息")
public class TradeRecordInfoDto implements Serializable {

    @ApiModelProperty("数据id")
    private Long id;

}
