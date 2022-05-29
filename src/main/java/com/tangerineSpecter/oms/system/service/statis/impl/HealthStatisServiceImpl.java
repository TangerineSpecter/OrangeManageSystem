package com.tangerinespecter.oms.system.service.statis.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.NumberUtil;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.utils.CollUtils;
import com.tangerinespecter.oms.system.domain.dto.statis.HealthStatisInfoDto;
import com.tangerinespecter.oms.system.domain.entity.UserHealth;
import com.tangerinespecter.oms.system.mapper.UserHealthMapper;
import com.tangerinespecter.oms.system.service.statis.IHealthStatisService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class HealthStatisServiceImpl implements IHealthStatisService {

    @Resource
    private UserHealthMapper userHealthMapper;

    @Override
    public HealthStatisInfoDto healthStatisInfo() {
        HealthStatisInfoDto result = new HealthStatisInfoDto();
        List<UserHealth> userHealths = userHealthMapper.queryUserWeight(UserContext.getUid());
        CollUtils.forEach(userHealths, userHealth -> {
            //格式化为MM-DD
            String recordTime = userHealth.getRecordTime();
            result.getDate().add(CharSequenceUtil.sub(recordTime, recordTime.length() - 5, recordTime.length()));
            result.getWeightData().add(Optional.ofNullable(userHealth.getWeight()).orElse(CollUtil.get(result.getWeightData(), -1)));
            result.getFatWeightData().add(Optional.ofNullable(userHealth.getFatWeight()).orElse(CollUtil.get(result.getFatWeightData(), -1)));
            result.getSleepDurationData().add(NumberUtil.div(Optional.ofNullable(userHealth.getSleepDuration()).orElse(0), (Number) 60, 1));
            result.getBmiData().add(Optional.ofNullable(userHealth.getBmi()).orElse(CollUtil.get(result.getBmiData(), -1)));
        });
        return result;
    }
}
