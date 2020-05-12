package com.tangerinespecter.oms.system.service.statis.impl;

import cn.hutool.core.util.NumberUtil;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.dto.statis.HealthStatisInfoDto;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.mapper.UserHealthMapper;
import com.tangerinespecter.oms.system.service.statis.IHealthStatisServer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HealthStatisServerImpl implements IHealthStatisServer {

    @Resource
    private UserHealthMapper userHealthMapper;

    @Override
    public ServiceResult healthStatisInfo() {
        List<UserHealth> userHealths = userHealthMapper.queryUserWeight(SystemUtils.getSystemUserId());
        List<BigDecimal> weightList = userHealths.stream().map(UserHealth::getWeight).collect(Collectors.toList());
        List<BigDecimal> fatWeight = userHealths.stream().map(UserHealth::getFatWeight).collect(Collectors.toList());
        List<Integer> pressureList = userHealths.stream().map(UserHealth::getPressure).collect(Collectors.toList());
        List<Integer> stepNumberList = userHealths.stream().map(UserHealth::getStepNumber).collect(Collectors.toList());
        List<BigDecimal> sleepDurationList = userHealths.stream().map(u -> NumberUtil.div(u.getSleepDuration(), (Number) 60, 1)).collect(Collectors.toList());
        List<String> date = userHealths.stream().map(UserHealth::getRecordTime).collect(Collectors.toList());
        HealthStatisInfoDto infoDto = HealthStatisInfoDto.builder().date(date).weightData(weightList)
                .fatWeightData(fatWeight).pressureData(pressureList).sleepDurationData(sleepDurationList)
                .stepNumberData(stepNumberList).build();
        return ServiceResult.success(infoDto);
    }
}
