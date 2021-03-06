package com.tangerinespecter.oms.system.service.user.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.user.UserHealthInfoVo;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.mapper.UserHealthMapper;
import com.tangerinespecter.oms.system.service.user.IUserHealthManageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class UserHealthManageServiceImpl implements IUserHealthManageService {
    /**
     * 类型：今天
     */
    private static final Integer TYPE_FOR_TODAY = 0;
    /**
     * 类型：昨天
     */
    private static final Integer TYPE_FOR_YESTERDAY = 1;

    @Resource
    private UserHealthMapper userHealthMapper;

    @Override
    public ServiceResult queryForPage(Model model, UserHealthQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<UserHealth> pageList = userHealthMapper.queryForPage(qo);
        PageInfo<UserHealth> userHealthInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, userHealthInfo.getTotal());
    }

    @Override
    public ServiceResult insert(Integer type) {
        if (type == null) {
            return ServiceResult.paramError();
        }
        UserHealth userHealth = UserHealth.builder().adminId(SystemUtils.getSystemUserId())
                .isDel(CommonConstant.IS_DEL_NO).build();
        if (TYPE_FOR_TODAY.equals(type)) {
            userHealth.setRecordTime(DateUtil.formatDate(new Date()));
        } else if (TYPE_FOR_YESTERDAY.equals(type)) {
            userHealth.setRecordTime(DateUtil.yesterday().toDateStr());
        } else {
            return ServiceResult.paramError();
        }
        userHealthMapper.insert(userHealth);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult update(UserHealthInfoVo data) {
        if (data.getId() == null) {
            return ServiceResult.paramError();
        }
        UserHealth userHealth = userHealthMapper.selectById(data.getId());
        BeanUtils.copyProperties(data, userHealth);
        userHealthMapper.updateById(userHealth);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult delete(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        UserHealth userHealth = userHealthMapper.selectById(id);
        userHealth.setIsDel(CommonConstant.IS_DEL_YES);
        userHealthMapper.updateById(userHealth);
        return ServiceResult.success();
    }

}
