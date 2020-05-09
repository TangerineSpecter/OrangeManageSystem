package com.tangerinespecter.oms.system.service.work.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.work.WorkCollectionInfoVo;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import com.tangerinespecter.oms.system.mapper.WorkCollectionMapper;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.work.IWorkCollectionService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WorkCollectionServiceImpl implements IWorkCollectionService {

    @Resource
    private WorkCollectionMapper workCollectionMapper;

    /**
     * 分页查询
     */
    @Override
    public ServiceResult queryForPage(Model model, WorkCollectionQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<WorkCollection> pageList = workCollectionMapper.queryForPage(qo);
        PageInfo<WorkCollection> collectionInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, collectionInfo.getTotal());
    }

    /**
     * 新增收藏
     */
    @Override
    public ServiceResult insert(WorkCollectionInfoVo data) {
        WorkCollection workCollection = new WorkCollection();
        BeanUtils.copyProperties(data, workCollection);
        workCollection.setIsDel(CommonConstant.IS_DEL_NO);
        workCollectionMapper.insert(workCollection);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult update(WorkCollectionInfoVo data) {
        if (data.getId() == null) {
            return ServiceResult.paramError();
        }
        WorkCollection workCollection = workCollectionMapper.selectById(data.getId());
        if (workCollection == null) {
            return ServiceResult.error(RetCode.WORK_COLLECTION_NOT_EXIST);
        }
        BeanUtils.copyProperties(data, workCollection);
        workCollectionMapper.updateById(workCollection);
        return ServiceResult.success();
    }

    /**
     * 删除收藏
     */
    @Override
    public ServiceResult delete(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        WorkCollection workCollection = workCollectionMapper.selectById(id);
        workCollection.setIsDel(CommonConstant.IS_DEL_YES);
        workCollectionMapper.updateById(workCollection);
        return ServiceResult.success();
    }
}
