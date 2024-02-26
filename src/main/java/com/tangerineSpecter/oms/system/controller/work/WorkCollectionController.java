package com.tangerinespecter.oms.system.controller.work;

import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.QueryObject;
import com.tangerinespecter.oms.common.query.WorkCollectionQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.dto.work.WorkCollectionInfoVo;
import com.tangerinespecter.oms.system.domain.entity.WorkCollection;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.work.IWorkCollectionService;
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
 * 收藏信息相关控制
 *
 * @author tangerinespecter
 * @version v0.1.2
 * @Date 2019年1月22日
 */
@ReWriteBody
@RestController
@RequiredArgsConstructor
@Api(tags = "收藏信息接口")
@RequestMapping("/work/collection")
public class WorkCollectionController {

    private final IWorkCollectionService workCollectionService;
    private final PageResultService pageResultService;

    /**
     * 收藏页面
     */
    @ApiOperation(value = "收藏页面")
    @RequiresPermissions("work:collection:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String collectionPage(HttpServletRequest request, HttpServletResponse response, Model model) {
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.WORK_COLLECTION_PAGE_KEY, "work/collection");
    }

    /**
     * 添加页面
     */
    @ApiOperation(value = "添加编辑页面")
    @GetMapping("/addPage")
    public ModelAndView addCollectionPage(Model model) {
        return ServiceResult.jumpPage("work/addEditCollection");
    }

    /**
     * 收藏列表
     */
    @ApiOperation("收藏列表")
    @PostMapping("/list")
    public PageInfo<WorkCollection> constellationPage(@RequestBody QueryObject<WorkCollectionQueryObject> qo) {
        return workCollectionService.queryForPage(qo);
    }

    /**
     * 新增收藏
     */
    @ApiOperation("新增收藏")
    @PostMapping("/insert")
    @LoggerInfo(value = "新增收藏", event = LogOperation.EVENT_ADD)
    public void insert(@Validated @RequestBody WorkCollectionInfoVo data) {
        workCollectionService.insert(data);
    }

    /**
     * 编辑收藏
     */
    @ApiOperation("编辑收藏")
    @PutMapping("/update")
    @LoggerInfo(value = "更新收藏", event = LogOperation.EVENT_UPDATE)
    public void update(@Validated(Update.class) @RequestBody WorkCollectionInfoVo data) {
        workCollectionService.update(data);
    }

    /**
     * 删除收藏
     */
    @ApiOperation("删除收藏")
    @DeleteMapping("/delete/{id}")
    @LoggerInfo(value = "删除收藏", event = LogOperation.EVENT_DELETE)
    public void delete(@PathVariable("id") @NotNull(message = "id不能为空") Long id) {
        workCollectionService.delete(id);
    }
}
