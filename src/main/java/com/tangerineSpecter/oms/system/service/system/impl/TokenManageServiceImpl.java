package com.tangerinespecter.oms.system.service.system.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tangerinespecter.oms.common.mapper.QueryWrapperX;
import com.tangerinespecter.oms.common.query.SystemTokenQueryObject;
import com.tangerinespecter.oms.system.convert.system.SystemConvert;
import com.tangerinespecter.oms.system.domain.vo.system.SystemTokenVo;
import com.tangerinespecter.oms.system.domain.entity.SystemToken;
import com.tangerinespecter.oms.system.mapper.SystemTokenMapper;
import com.tangerinespecter.oms.system.service.system.ITokenManageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author TangerineSpecter
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TokenManageServiceImpl implements ITokenManageService {

    private final SystemTokenMapper systemTokenMapper;

    @Override
    public List<SystemToken> list(SystemTokenQueryObject qo) {
        QueryWrapper<SystemToken> queryWrapper = new QueryWrapperX<SystemToken>()
                .eqIfPresent("type", qo.getType())
                .likeIfPresent("name", qo.getName());
        return systemTokenMapper.selectList(queryWrapper);
    }

    @Override
    public void insert(SystemTokenVo param) {
        systemTokenMapper.insert(SystemConvert.INSTANCE.convert(param));
    }

    @Override
    public void update(SystemTokenVo param) {
        systemTokenMapper.updateById(SystemConvert.INSTANCE.convert(param));
    }

    @Override
    public void delete(Long id) {
        systemTokenMapper.deleteById(id);
    }
}
