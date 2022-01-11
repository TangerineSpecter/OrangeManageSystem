package com.tangerinespecter.oms.system.controller.system;

import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码控制器
 *
 * @author 丢失的橘子
 * @version 0.4.1
 * @date 2022年1月7日 00:51:34
 */
@RestController
@Api(tags = "验证码接口")
@RequestMapping("system/captcha")
public class CaptchaController {
	
	/**
	 * 验证码生成
	 *
	 * @param request  请求报文
	 * @param response 响应报文
	 */
	@ApiOperation("验证码生成")
	@GetMapping("generate")
	public void generate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		CaptchaUtil.out(request, response);
	}
	
}
