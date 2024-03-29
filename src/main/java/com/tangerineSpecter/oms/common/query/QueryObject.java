package com.tangerinespecter.oms.common.query;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 分页查询
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @date 2019年1月9日
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryObject<T extends Serializable> implements Serializable {

    /**
     * 搜索参数
     */
    @ApiModelProperty(value = "搜索参数", hidden = true)
    private T searchParams;
    /**
     * 默认第一页
     */
    @ApiModelProperty("页数")
    private Integer page = 1;
    /**
     * 默认一页十条
     */
    @ApiModelProperty("页面大小")
    private Integer limit = 10;

    public QueryObject(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    public void initAllPage() {
        this.limit = Integer.MAX_VALUE;
    }
}
