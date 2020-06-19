package com.tangerinespecter.oms.system.service.data.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.constants.RetCode;
import com.tangerinespecter.oms.common.query.TradeLogicQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.SystemUtils;
import com.tangerinespecter.oms.system.domain.entity.DataTradeLogic;
import com.tangerinespecter.oms.system.domain.vo.data.AddTradeLogicVo;
import com.tangerinespecter.oms.system.domain.vo.data.EditTradeLogicVo;
import com.tangerinespecter.oms.system.mapper.DataTradeLogicMapper;
import com.tangerinespecter.oms.system.service.data.IDataTradeLogicService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataTradeLogicServiceImpl implements IDataTradeLogicService {

    @Resource
    private DataTradeLogicMapper dataTradeLogicMapper;

    @Override
    public ServiceResult queryForPage(TradeLogicQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<DataTradeLogic> pageList = dataTradeLogicMapper.queryForPage(qo);
        PageInfo<DataTradeLogic> tradeLogicPageInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, tradeLogicPageInfo.getTotal());
    }

    @Override
    public ServiceResult insertInfo(AddTradeLogicVo vo) {
        DataTradeLogic tradeLogic = DataTradeLogic.builder().name(vo.getName()).entryDate(vo.getEntryDate())
                .entryPoint(vo.getEntryPoint()).profitPoint(vo.getProfitPoint())
                .type(vo.getType()).lossPoint(vo.getLossPoint()).tradeLogic(vo.getTradeLogic())
                .adminId(SystemUtils.getSystemUserId()).build();
        dataTradeLogicMapper.insert(tradeLogic);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult updateInfo(EditTradeLogicVo vo) {
        DataTradeLogic dataTradeLogic = dataTradeLogicMapper.selectById(vo.getId());
        if (dataTradeLogic == null) {
            return ServiceResult.error(RetCode.TRADE_LOGIC_NOT_EXIST);
        }
        dataTradeLogic.setEntryPoint(vo.getEntryPoint());
        dataTradeLogic.setExitPoint(vo.getExitPoint());
        dataTradeLogic.setClosingPrice(vo.getClosingPrice());
        dataTradeLogic.setConclusion(vo.getConclusion());
        dataTradeLogic.setStatus(vo.getStatus());
        dataTradeLogic.setExitDate(vo.getExitDate());
        dataTradeLogicMapper.updateById(dataTradeLogic);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult deleteInfo(Long id) {
        if (id == null) {
            return ServiceResult.paramError();
        }
        dataTradeLogicMapper.deleteById(id);
        return ServiceResult.success();
    }
}
