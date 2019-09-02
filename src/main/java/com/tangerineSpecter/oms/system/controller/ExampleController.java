package com.tangerinespecter.oms.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 示例控制
 *
 * @author TangerineSpecter
 * @date 2019年09月02日13:50:44
 */
@Controller
public class ExampleController {

    /**
     * 表格示例
     *
     * @return
     */
    @RequestMapping("/table")
    public String table() {
        return "example/table";
    }

    /**
     * 普通表单
     *
     * @return
     */
    @RequestMapping("/form")
    public String form() {
        return "example/form";
    }

    /**
     * 分步表单
     *
     * @return
     */
    @RequestMapping("/form-step")
    public String formStep() {
        return "example/form-step";
    }

    /**
     * 按钮示例
     *
     * @return
     */
    @RequestMapping("/button")
    public String button() {
        return "example/button";
    }

    /**
     * 弹出层
     *
     * @return
     */
    @RequestMapping("/layer")
    public String layer() {
        return "example/layer";
    }

    /**
     * 颜色选择器
     *
     * @return
     */
    @RequestMapping("/color-select")
    public String colorSelect() {
        return "example/color-select";
    }

    /**
     * 富文本编辑器
     *
     * @return
     */
    @RequestMapping("/editor")
    public String editor() {
        return "example/editor";
    }

    /**
     * 图标列表
     *
     * @return
     */
    @RequestMapping("/icon")
    public String icon() {
        return "example/icon";
    }

    /**
     * 富文本编辑器
     *
     * @return
     */
    @RequestMapping("/icon-picker")
    public String iconPicker() {
        return "example/icon-picker";
    }

    /**
     * 下拉选择框
     *
     * @return
     */
    @RequestMapping("/table-select")
    public String tableSelect() {
        return "example/table-select";
    }

    /**
     * 文件上传
     *
     * @return
     */
    @RequestMapping("/upload")
    public String upload() {
        return "example/upload";
    }
}
