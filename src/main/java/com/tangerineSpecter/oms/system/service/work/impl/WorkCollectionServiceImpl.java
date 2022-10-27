package com.tangerinespecter.oms.system.service.work.impl;

import cn.hutool.core.lang.Assert;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.system.domain.dto.work.WorkCollectionInfoVo;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import com.tangerinespecter.oms.system.mapper.WorkCollectionMapper;
import com.tangerinespecter.oms.system.service.work.IWorkCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkCollectionServiceImpl implements IWorkCollectionService {

    private final WorkCollectionMapper workCollectionMapper;

    /**
     * 分页查询
     */
    @Override
    public List<WorkCollection> list(WorkCollectionQueryObject qo) {
        return workCollectionMapper.queryForPage(qo);
    }

    /**
     * 新增收藏
     */
    @Override
    public void insert(WorkCollectionInfoVo data) {
        WorkCollection workCollection = new WorkCollection();
        BeanUtils.copyProperties(data, workCollection);
        workCollection.setIsDel(GlobalBoolEnum.FALSE.getValue());
        workCollectionMapper.insert(workCollection);
    }

    @Override
    public void update(WorkCollectionInfoVo data) {
        WorkCollection workCollection = workCollectionMapper.selectById(data.getId());
        Assert.isTrue(workCollection != null, () -> new BusinessException(RetCode.WORK_COLLECTION_NOT_EXIST));
        BeanUtils.copyProperties(data, workCollection);
        workCollectionMapper.updateById(workCollection);
    }

    /**
     * 删除收藏
     */
    @Override
    public void delete(Long id) {
        WorkCollection workCollection = workCollectionMapper.selectById(id);
        workCollection.setIsDel(GlobalBoolEnum.TRUE.getValue());
        int i = workCollectionMapper.updateById(workCollection);
        Assert.isTrue(i < 1, () -> new BusinessException(RetCode.WORK_COLLECTION_NOT_EXIST));
    }
}
