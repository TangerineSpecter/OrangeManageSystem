package com.tangerinespecter.oms.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ParamUtils;
import com.tangerinespecter.oms.system.domain.dto.system.SystemBulletinInfoVo;
import com.tangerinespecter.oms.system.domain.entity.SystemBulletin;
import com.tangerinespecter.oms.system.domain.entity.SystemMenu;
import com.tangerinespecter.oms.system.mapper.SystemBulletinMapper;
import com.tangerinespecter.oms.system.service.system.ISystemBulletinService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

@Service
public class SystemBulletinServiceImpl implements ISystemBulletinService {

    @Resource
    private SystemBulletinMapper systemBulletinMapper;

    @Override
    public ServiceResult queryForPage(Model model, SystemBulletinQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<SystemBulletin> pageList = systemBulletinMapper.queryForPage(qo);
        PageInfo<SystemBulletin> bulletinInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, bulletinInfo.getTotal());
    }

    @Override
    public ServiceResult insert(SystemBulletinInfoVo data) {
        SystemBulletin systemBulletin = SystemBulletin.builder().title(data.getTitle()).content(data.getContent())
                .isDel(CommonConstant.IS_DEL_NO).top(CommonConstant.IS_NOT_TOP).build();
        systemBulletinMapper.insert(systemBulletin);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult update(SystemBulletinInfoVo data) {
        if (data.getId() == null) {
            return ServiceResult.paramError();
        }
        SystemBulletin systemBulletin = systemBulletinMapper.selectById(data.getId());
        systemBulletin.setTitle(data.getTitle());
        systemBulletin.setContent(data.getContent());
        systemBulletinMapper.updateById(systemBulletin);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult delete(Long id) {
        systemBulletinMapper.deleteById(id);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult topInfo(Long id) {
        SystemBulletin systemBulletin = systemBulletinMapper.selectById(id);
        if (systemBulletin == null) {
            return ServiceResult.error(RetCode.SYSTEM_BULLETIN_NOT_EXIST);
        }
        QueryWrapper<SystemBulletin> queryWrapper = new QueryWrapper<SystemBulletin>().eq(ParamUtils.TOP, CommonConstant.IS_TOP);
        Integer count = systemBulletinMapper.selectCount(queryWrapper);
        if (CommonConstant.IS_NOT_TOP.equals(systemBulletin.getTop()) && count >= 1) {
            return ServiceResult.error(RetCode.SYSTEM_BULLETIN_MORE_THAN_UPPER);
        }
        systemBulletin.setTop(CommonConstant.IS_TOP.equals(systemBulletin.getTop()) ? CommonConstant.IS_NOT_TOP : CommonConstant.IS_TOP);
        systemBulletinMapper.updateById(systemBulletin);
        return ServiceResult.success();
    }
}
