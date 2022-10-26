package com.tangerinespecter.oms.system.controller.data;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.AccessLimit;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.FoodLibraryQueryObject;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.entity.DataFoodLibrary;
import com.tangerinespecter.oms.system.domain.vo.data.FoodLibraryInfoVo;
import com.tangerinespecter.oms.system.service.data.IDataFoodLibraryService;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.valid.Update;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * 食物数据相关
 *
 * @author 丢失的橘子
 * @version 0.5.1
 * @Date 2022年10月26日17:30:15
 */
@ReWriteBody
@RestController
@Api(tags = "食物库接口")
@RequiredArgsConstructor
@RequestMapping("/data/food-library")
public class DataFoodController {

    private final IDataFoodLibraryService dataFoodLibraryService;
    private final PageResultService pageResultService;

    @ApiOperation("食物库页面")
    @RequiresPermissions("data:food-library:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String pageInfo(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getFoodLibraryPageKey, "data/foodLibrary");
    }

    @ApiOperation("食物记录列表")
    @AccessLimit(maxCount = 10)
    @GetMapping("/list")
    public PageInfo<DataFoodLibrary> listInfo(QueryObject<FoodLibraryQueryObject> qo) {
        return dataFoodLibraryService.queryForPage(qo);
    }

    @ApiOperation("添加编辑页面")
    @GetMapping("/addPage")
    public ModelAndView addTradeRecordPage() {
        return ServiceResult.jumpPage("data/addEditFoodLibrary");
    }

    @ApiOperation("添加食物记录")
    @PostMapping("/insert")
    @LoggerInfo(value = "添加食物数据", event = LogOperation.EVENT_ADD)
    public void insertInfo(@Validated @RequestBody FoodLibraryInfoVo vo) {
        dataFoodLibraryService.insertInfo(vo);
    }

    @ApiOperation("编辑食物记录")
    @PutMapping("/update")
    @LoggerInfo(value = "编辑食物数据", event = LogOperation.EVENT_UPDATE)
    public void updateInfo(@Validated(Update.class) @RequestBody FoodLibraryInfoVo vo) {
        dataFoodLibraryService.updateInfo(vo);
    }

    @ApiOperation("食物记录信息")
    @GetMapping("/info/{id}")
    public DataFoodLibrary detailInfo(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
        return dataFoodLibraryService.detailInfo(id);
    }

    @ApiOperation("删除食物记录")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除食物数据", event = LogOperation.EVENT_DELETE)
    public void deleteInfo(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
        dataFoodLibraryService.deleteInfo(id);
    }

}
