package com.tangerinespecter.oms.system.controller.table;

import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.common.utils.ServiceKey;
import com.tangerinespecter.oms.system.service.table.DataConstellationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 星座数据相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月8日
 */
@Controller
public class DataConstellationController {

    @Resource
    private DataConstellationService dataConstellationService;

    /**
     * 星座页面
     */
    @RequestMapping(ServiceKey.Constellation.CONSTELLATION_PAGE_LIST)
    public String constellationPage(Model model, ConstellationQueryObject qo) {
        dataConstellationService.queryForPage(model, qo);
        return "data/constellaction";
    }
}
