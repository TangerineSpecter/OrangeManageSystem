package com.tangerinespecter.oms.system.service.user.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.system.convert.user.HealthConvert;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.domain.enums.HealthRecordTypeEnum;
import com.tangerinespecter.oms.system.domain.vo.user.UserHealthInfoVo;
import com.tangerinespecter.oms.system.mapper.UserHealthMapper;
import com.tangerinespecter.oms.system.service.user.IUserHealthManageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserHealthManageServiceImpl implements IUserHealthManageService {

    private final UserHealthMapper userHealthMapper;

    @Override
    public List<UserHealth> list(UserHealthQueryObject qo) {
        return userHealthMapper.queryForPage(qo);
    }

    @Override
    public void insert(Integer type) {
        UserHealth userHealth = new UserHealth();
        if (HealthRecordTypeEnum.TODAY_RECORD_TYPE.getValue().equals(type)) {
            userHealth.setRecordTime(DateUtil.formatDate(new Date()));
        } else if (HealthRecordTypeEnum.YESTERDAY_RECORD_TYPE.getValue().equals((type))) {
            userHealth.setRecordTime(DateUtil.yesterday().toDateStr());
        } else {
            throw new BusinessException(RetCode.PARAM_ERROR);
        }
        UserHealth existInfo = userHealthMapper.selectOneByRecordTime(HealthRecordTypeEnum.getDate(type));
        Assert.isTrue(existInfo == null, () -> new BusinessException(RetCode.HEALTH_RECORD_EXIST));
        userHealthMapper.insert(userHealth);
    }

    @Override
    public void update(UserHealthInfoVo data) {
        userHealthMapper.updateById(HealthConvert.INSTANCE.convert(data));
    }

    @Override
    public void delete(Long id) {
        userHealthMapper.deleteById(id);
    }

}
