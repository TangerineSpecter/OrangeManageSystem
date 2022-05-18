package com.tangerinespecter.oms.system.service.user.impl;

import cn.hutool.core.date.DateUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.enums.HealthRecordTypeEnum;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.convert.user.HealthConvert;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.domain.vo.user.UserHealthInfoVo;
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
        UserHealth userHealth = UserHealth.builder().uid(SystemUtils.getSystemUserId())
                .isDel(CommonConstant.IS_DEL_NO).build();
        if (HealthRecordTypeEnum.TODAY_RECORD_TYPE.getValue().equals(type)) {
            userHealth.setRecordTime(DateUtil.formatDate(new Date()));
        } else if (HealthRecordTypeEnum.YESTERDAY_RECORD_TYPE.getValue().equals((type))) {
            userHealth.setRecordTime(DateUtil.yesterday().toDateStr());
        } else {
            return ServiceResult.paramError();
        }
        UserHealth existInfo = userHealthMapper.selectOneByRecordTime(HealthRecordTypeEnum.getDate(type));
        if (existInfo != null) {
            return ServiceResult.error(RetCode.HEALTH_RECORD_EXIST);
        }
        userHealthMapper.insert(userHealth);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult update(UserHealthInfoVo data) {
        UserHealth userHealth = userHealthMapper.selectById(data.getId());
        BeanUtils.copyProperties(data, userHealth);
        HealthConvert.INSTANCE.convert(data);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult delete(Long id) {
        return ServiceResult.success(userHealthMapper.deleteById(id));
    }

}
