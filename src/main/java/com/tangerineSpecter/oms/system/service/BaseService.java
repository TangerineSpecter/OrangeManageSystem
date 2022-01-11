package com.tangerinespecter.oms.system.service;

import com.github.pagehelper.IPage;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.QueryObject;

import java.util.List;

/**
 * @author 丢失的橘子
 */
public interface BaseService<Param, Result> {

    /**
     * 分页查询
     *
     * @param param 请求参数DTO
     * @return 分页集合
     */
    default PageInfo<Result> queryForPage(QueryObject<Param> param) {
        return PageHelper.startPage(param.getPage(), param.getLimit())
                .doSelectPageInfo(() -> list(param.getSearchParams()));
    }

    /**
     * 集合查询
     *
     * @param param 查询参数
     * @return 查询响应
     */
    List<Result> list(Param param);
}
