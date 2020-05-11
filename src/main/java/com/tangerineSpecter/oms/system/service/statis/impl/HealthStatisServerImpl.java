package com.tangerinespecter.oms.system.service.statis.impl;

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
    public ServiceResult weightStatisInfo() {
        List<UserHealth> userHealths = userHealthMapper.queryUserWeight(SystemUtils.getSystemUserId());
        List<BigDecimal> weightList = userHealths.stream().map(UserHealth::getWeight).collect(Collectors.toList());
        List<String> date = userHealths.stream().map(UserHealth::getRecordTime).collect(Collectors.toList());
        Collections.reverse(weightList);
        Collections.reverse(date);
        HealthStatisInfoDto infoDto = HealthStatisInfoDto.builder().weights(weightList).date(date).build();
        return ServiceResult.success(infoDto);
    }
}
