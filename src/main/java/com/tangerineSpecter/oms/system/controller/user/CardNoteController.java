package com.tangerinespecter.oms.system.controller.user;

import cn.hutool.core.date.DateUtil;
import com.tangerinespecter.oms.common.anno.LoggerInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.enums.LogOperation;
import com.tangerinespecter.oms.common.query.UserCardNoteQueryObject;
import com.tangerinespecter.oms.common.redis.PageModelKey;
import com.tangerinespecter.oms.common.result.ServiceResult;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteInfoVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteTagAssocVo;
import com.tangerinespecter.oms.system.domain.vo.user.CardNoteTagVo;
import com.tangerinespecter.oms.system.service.page.PageResultService;
import com.tangerinespecter.oms.system.service.user.ICardNoteService;
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
import java.util.Date;

/**
 * 卡片笔记
 *
 * @author 丢失的橘子
 * @date 2022年1月16日 21:56:51
 */
@Api(tags = "卡片笔记模块")
@RestController
@ReWriteBody
@RequiredArgsConstructor
@RequestMapping("/user/card-note")
public class CardNoteController {

    private final PageResultService pageResultService;
    private final ICardNoteService cardNoteService;

    @ApiOperation(value = "卡片笔记界面")
    @RequiresPermissions("user:card-note:page")
    @GetMapping(value = "/page", produces = "text/html;charset=UTF-8")
    public String cardNotePage(HttpServletRequest request, HttpServletResponse response, Model model) {
        model.addAttribute("noteList", cardNoteService.queryForPage(new UserCardNoteQueryObject()).getList());
        int offsetDay = 7 - DateUtil.thisDayOfWeek();
        model.addAttribute("rangeDate", new String[]{DateUtil.formatDate(DateUtil.offsetDay(new Date(), -90 + offsetDay)), DateUtil.formatDate(DateUtil.offsetDay(new Date(), offsetDay))});
        model.addAttribute("noteInfo", cardNoteService.noteInfo());
        return pageResultService.getPageHtmlContent(request, response, model, PageModelKey.getCardNotePageKey, "user/cardNoteManage");
    }

    /**
     * 标签设置页面
     */
    @ApiOperation("标签设置页面")
    @GetMapping("/tagSetting/{noteId}")
    public ModelAndView tagSetting(Model model, @PathVariable("noteId") Long noteId) {
        model.addAttribute("allTags", cardNoteService.getAllTags());
        model.addAttribute("haveTags", cardNoteService.haveTagIds(noteId));
        return ServiceResult.jumpPage("user/noteTagSetting");
    }

    @ApiOperation(value = "卡片笔记列表")
    @GetMapping(value = "/list")
    public ModelAndView queryForPage(Model model, UserCardNoteQueryObject qo) {
        model.addAttribute("noteList", cardNoteService.queryForPage(qo).getList());
        return ServiceResult.jumpPage("user/cardNoteManage::noteCards");
    }

    @ApiOperation(value = "随机漫步")
    @GetMapping(value = "/random-note")
    public ModelAndView randomNote(Model model) {
        model.addAttribute("noteList", cardNoteService.randOne());
        return ServiceResult.jumpPage("user/cardNoteManage::noteCards");
    }

    @ApiOperation(value = "新增卡片笔记")
    @LoggerInfo(value = "新增卡片笔记", event = LogOperation.EVENT_ADD)
    @PostMapping(value = "/insert")
    public void insert(@RequestBody @Validated CardNoteInfoVo vo) {
        cardNoteService.insert(vo);
    }

    @ApiOperation(value = "新增笔记标签")
    @LoggerInfo(value = "新增笔记标签", event = LogOperation.EVENT_ADD)
    @PostMapping(value = "/insert-tag")
    public void insertTag(@RequestBody @Validated CardNoteTagVo vo) {
        cardNoteService.insertTag(vo);
    }

    @ApiOperation(value = "修改标签")
    @LoggerInfo(value = "修改标签", event = LogOperation.EVENT_UPDATE)
    @PutMapping(value = "/update-tag")
    public void updateTag(@RequestBody @Validated(Update.class) CardNoteTagVo vo) {
        cardNoteService.updateTag(vo);
    }

    @ApiOperation(value = "修改笔记标签关联")
    @LoggerInfo(value = "修改笔记标签关联", event = LogOperation.EVENT_UPDATE)
    @PutMapping(value = "/update-tag-assoc")
    public void updateTagAssoc(@RequestBody @Validated CardNoteTagAssocVo vo) {
        cardNoteService.updateTagAssoc(vo);
    }

    @ApiOperation(value = "恢复卡片笔记")
    @LoggerInfo(value = "恢复卡片笔记", event = LogOperation.EVENT_UPDATE)
    @PutMapping(value = "/{id}/restore")
    public void restore(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
        cardNoteService.restore(id);
    }

    @ApiOperation(value = "删除卡片笔记")
    @LoggerInfo(value = "删除卡片笔记", event = LogOperation.EVENT_DELETE)
    @DeleteMapping(value = "/delete/{id}")
    public void delete(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
        cardNoteService.delete(id);
    }

    @ApiOperation(value = "完全删除卡片笔记")
    @LoggerInfo(value = "完全删除卡片笔记", event = LogOperation.EVENT_DELETE)
    @DeleteMapping(value = "/force-delete/{id}")
    public void forceDelete(@NotNull(message = "id不能为null") @PathVariable("id") Long id) {
        cardNoteService.forceDelete(id);
    }

    @ApiOperation(value = "删除笔记标签")
    @LoggerInfo(value = "删除笔记标签", event = LogOperation.EVENT_DELETE)
    @DeleteMapping(value = "/delete-tag/{tagId}")
    public void deleteTag(@NotNull(message = "标签id不能为空") @PathVariable("tagId") Long tagId) {
        cardNoteService.deleteTag(tagId);
    }
}
