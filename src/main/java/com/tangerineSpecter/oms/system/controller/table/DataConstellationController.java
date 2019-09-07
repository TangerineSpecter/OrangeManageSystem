package com.tangerinespecter.oms.system.controller.table;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.query.ConstellationQueryObject;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataConstellation;
import com.tangerinespecter.oms.system.service.table.DataConstellationService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 星座数据相关控制
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月8日
 */
@Controller
@RequestMapping("/table/constellation")
public class DataConstellationController {

    @Resource
    private DataConstellationService dataConstellationService;

    /**
     * 星座页面
     */
    @RequestMapping("/page")
    public String pageInfo() {
        return "data/constellation";
    }

    /**
     * 星座列表
     */
    @ResponseBody
    @RequestMapping("/list")
    public ServiceResult listInfo(ConstellationQueryObject qo) {
        return dataConstellationService.queryForPage(qo);
    }
}
