package com.tangerinespecter.oms.system.service.table;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.dao.entity.DataConstellation;
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
    @Resource
    private PageResultService pageResultService;

    public List<DataConstellation> queryListAll() {
        return dataConstellationMapper.selectList(null);
    }

    /**
     * 分页查询
     */
    public void queryForPage(Model model, ConstellationQueryObject qo) {
        PageHelper.startPage(qo.getPage(), qo.getSize());
        List<DataConstellation> pageList = dataConstellationMapper.queryForPage(qo);
        PageInfo<DataConstellation> constellationInfo = new PageInfo<>(pageList);
        pageResultService.queryForPage(model, constellationInfo.getList(), constellationInfo.getTotal(), qo.getPage(),
                constellationInfo.getPages(), ServiceKey.Constellation.CONSTELLATION_PAGE_LIST);
    }
}
