package com.tangerinespecter.oms.common.interceptor;

import cn.hutool.core.convert.Convert;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import com.tangerinespecter.oms.common.anno.ReWriteBody;
import com.tangerinespecter.oms.common.result.ServiceResult;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * <p>rewrite response body<p/>
 *
 * @author TangerineSpecter
 */
@RestControllerAdvice
public class RewriteResponseBody implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return returnType.getDeclaringClass().isAnnotationPresent(ReWriteBody.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (body instanceof String) {
            return body;
        }
        if (body instanceof PageInfo) {
            return ServiceResult.pageSuccess((PageInfo<?>) body);
        }
        return body instanceof ServiceResult ? body : ServiceResult.success(body);
    }
}