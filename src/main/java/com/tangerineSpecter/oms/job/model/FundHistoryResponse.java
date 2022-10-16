package com.tangerinespecter.oms.job.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * 基金历史数据查询响应结果
 *
 * @author 丢失的橘子
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class FundHistoryResponse extends DjFundApiBaseResponse<FundHistoryResponse> implements Serializable {

    @JSONField(name = "current_page")
    private Integer currentPage;
    @JSONField(name = "size")
    private Integer size;
    @JSONField(name = "total_items")
    private Integer totalItems;
    @JSONField(name = "total_pages")
    private Integer totalPages;
    @JSONField(name = "items")
    private List<FundHistoryData> items;
}
