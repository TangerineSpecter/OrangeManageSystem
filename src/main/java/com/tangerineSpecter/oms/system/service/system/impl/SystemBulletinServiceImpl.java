package com.tangerinespecter.oms.system.service.system.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.page.PageMethod;
import com.tangerinespecter.oms.common.constants.CommonConstant;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.enums.GlobalBoolEnum;
import com.tangerinespecter.oms.common.exception.BusinessException;
import com.tangerinespecter.oms.common.query.SystemBulletinQueryObject;
import com.tangerinespecter.oms.common.utils.ParamUtils;
import com.tangerinespecter.oms.system.domain.dto.system.SystemBulletinInfoVo;
import com.tangerinespecter.oms.system.domain.entity.SystemBulletin;
import com.tangerinespecter.oms.system.mapper.SystemBulletinMapper;
import com.tangerinespecter.oms.system.service.system.ISystemBulletinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
@RequiredArgsConstructor
public class SystemBulletinServiceImpl implements ISystemBulletinService {

    private final SystemBulletinMapper systemBulletinMapper;

    @Override
    public PageInfo<SystemBulletin> queryForPage(Model model, SystemBulletinQueryObject qo) {
        return PageMethod.startPage(qo.getPage(), qo.getLimit())
                .doSelectPageInfo(() -> systemBulletinMapper.queryForPage(qo));
    }

    @Override
    public void insert(SystemBulletinInfoVo data) {
        SystemBulletin systemBulletin = SystemBulletin.builder().title(data.getTitle()).content(data.getContent())
                .isDel(GlobalBoolEnum.FALSE.getValue()).top(CommonConstant.IS_NOT_TOP).build();
        systemBulletinMapper.insert(systemBulletin);
    }

    @Override
    public void update(SystemBulletinInfoVo data) {
        SystemBulletin systemBulletin = systemBulletinMapper.selectById(data.getId());
        systemBulletin.setTitle(data.getTitle());
        systemBulletin.setContent(data.getContent());
        systemBulletinMapper.updateById(systemBulletin);
    }

    @Override
    public void delete(Long id) {
        systemBulletinMapper.deleteById(id);
    }

    @Override
    public void topInfo(Long id) {
        SystemBulletin systemBulletin = systemBulletinMapper.selectById(id);
        Assert.notNull(systemBulletin, () -> new BusinessException(RetCode.SYSTEM_BULLETIN_NOT_EXIST));
        QueryWrapper<SystemBulletin> queryWrapper = new QueryWrapper<SystemBulletin>().eq(ParamUtils.TOP, CommonConstant.IS_TOP);
        Long count = systemBulletinMapper.selectCount(queryWrapper);
        Assert.isTrue(CommonConstant.IS_NOT_TOP.equals(systemBulletin.getTop()) && count < 1, () -> new BusinessException(RetCode.SYSTEM_BULLETIN_MORE_THAN_UPPER));
        systemBulletin.setTop(CommonConstant.IS_TOP.equals(systemBulletin.getTop()) ? CommonConstant.IS_NOT_TOP : CommonConstant.IS_TOP);
        systemBulletinMapper.updateById(systemBulletin);
    }
}
