package com.tangerinespecter.oms.system.service.user.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.UserHealthQueryObject;
import com.tangerinespecter.oms.system.convert.user.HealthConvert;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.domain.enums.HealthRecordTypeEnum;
import com.tangerinespecter.oms.system.domain.vo.user.UserHealthInfoVo;
import com.tangerinespecter.oms.system.mapper.UserHealthMapper;
import com.tangerinespecter.oms.system.service.user.IUserHealthManageService;
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
    public PageInfo<UserHealth> queryForPage(Model model, UserHealthQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<UserHealth> pageList = userHealthMapper.queryForPage(qo);
        return new PageInfo<>(pageList);
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
