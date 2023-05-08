package com.tangerinespecter.oms.common.mapper.pojo;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouliangjun
 */
@ApiModel("分页结果")
@Data
public final class PageResult<T> implements Serializable {

    @ApiModelProperty(value = "数据", required = true)
    private List<T> list;

    @ApiModelProperty(value = "总量", required = true)
    private Long total;

    @ApiModelProperty(value = "页数", required = true)
    private Integer pageNum;

    @ApiModelProperty(value = "页面大小", required = true)
    private Integer pageSize;

    public PageResult() {
    }

    public PageResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public PageResult(List<T> list) {
        PageInfo<T> pageInfo = new PageInfo<>(list);
        this.list = list;
        this.total = pageInfo.getTotal();
        this.pageNum = pageInfo.getPageNum();
        this.pageSize = pageInfo.getPageSize();
    }

    public PageResult(Long total) {
        this.list = new ArrayList<>();
        this.total = total;
    }

    public static <T> PageResult<T> empty() {
        return new PageResult<>(0L);
    }

}
