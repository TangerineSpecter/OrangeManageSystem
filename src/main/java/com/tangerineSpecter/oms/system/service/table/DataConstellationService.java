package com.tangerinespecter.oms.system.service.table;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.mapper.DataConstellationMapper;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataConstellationService {

    @Resource
    private DataConstellationMapper dataConstellationMapper;

    /**
     * 分页查询
     */
    public ServiceResult queryForPage(ConstellationQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getLimit());
        List<DataConstellation> pageList = dataConstellationMapper.queryForPage(qo);
        PageInfo<DataConstellation> constellationInfo = new PageInfo<>(pageList);
        return ServiceResult.pageSuccess(pageList, constellationInfo.getTotal());
    }
}
