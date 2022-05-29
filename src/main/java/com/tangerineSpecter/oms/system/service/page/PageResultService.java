package com.tangerinespecter.oms.system.service.page;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.tangerinespecter.oms.common.constants.SystemConstant;
import com.tangerinespecter.oms.common.context.UserContext;
import com.tangerinespecter.oms.common.redis.KeyPrefix;
import com.tangerinespecter.oms.system.service.helper.RedisHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 分页处理
 *
 * @author TangerineSpecter
 * @version v0.0.5
 * @Date 2019年1月9日
 */
@Service
@RequiredArgsConstructor
public class PageResultService {

    private final ThymeleafViewResolver thymeleafViewResolver;
    private final RedisHelper redisHelper;

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

    /**
     * 获取模板html内容
     *
     * @param request  请求
     * @param response 响应
     * @param model    model
     * @param redisKey 缓存key
     * @param pageUrl  页面跳转地址
     * @return html模板内容
     */
    public String getPageHtmlContent(HttpServletRequest request, HttpServletResponse response, Model model, KeyPrefix redisKey, String pageUrl) {
        String html = (String) redisHelper.get(redisKey, UserContext.getUid());
        if (!CharSequenceUtil.isBlank(html)) {
            return html;
        }
        IWebContext ctx = new WebContext(request, response, request.getServletContext(),
                request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process(pageUrl, ctx);
        if (!StrUtil.isBlank(html)
                && SystemConstant.NO_CACHE.equals(SystemConstant.systemConfig.getCacheTime())) {
            redisHelper.set(redisKey, UserContext.getUid(), html);
        }
        return html;
    }
}
