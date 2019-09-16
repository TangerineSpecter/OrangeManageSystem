package com.tangerinespecter.oms.system.service.work.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import com.tangerinespecter.oms.system.mapper.WorkCollectionMapper;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.work.IWorkCollectionService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkCollectionServiceImpl implements IWorkCollectionService {

    @Resource
    private WorkCollectionMapper workCollectionMapper;
    @Resource
    private PageResultService pageResultService;

    /**
     * 分页查询
     */
    @Override
    public void queryForPage(Model model, WorkCollectionQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<WorkCollection> pageList = workCollectionMapper.queryForPage(qo);
        PageInfo<WorkCollection> collectionInfo = new PageInfo<>(pageList);
        pageResultService.queryForPage(model, collectionInfo.getList(), collectionInfo.getTotal(), qo.getPage(),
                collectionInfo.getPages());
    }

    /**
     * 新增收藏
     */
    @Override
    public ServiceResult insert(WorkCollection data) {
        data.setSort(CommonConstant.Number.COMMON_NUMBER_ZERO);
        data.setIsDel(CommonConstant.IS_DEL_NO);
        workCollectionMapper.insert(data);
        return ServiceResult.success();
    }

    /**
     * 删除收藏
     */
    @Override
    public ServiceResult delete(Long id) {
        workCollectionMapper.deleteById(id);
        return ServiceResult.success();
    }
}
