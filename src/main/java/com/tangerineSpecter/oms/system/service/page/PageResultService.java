package com.tangerinespecter.oms.system.service.page;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

/**
 * 分页处理
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月9日
 */
@Service
public class PageResultService {

    /**
     * 分页处理
     *
     * @param model
     * @param list  列表
     * @param total 总数
     * @param page  页数
     */
    public void queryForPage(Model model, List<?> list, Long total, Integer page, Integer totalPage) {
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPage", totalPage);
        model.addAttribute("list", list);
        model.addAttribute("total", total.intValue());
    }
}
