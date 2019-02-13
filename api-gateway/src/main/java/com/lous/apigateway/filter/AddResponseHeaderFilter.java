package com.lous.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.POST_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SEND_RESPONSE_FILTER_ORDER;

/**
 * @ClassName : AddResponseHeaderFilter
 * @Description : 向返回结果头部写一些东西
 *
 * @author : Loushuai
 * @since : 2019-01-28
 **/
@Component
public class AddResponseHeaderFilter extends ZuulFilter {
    /**
     * filter 类型
     *  这里返回 post
     * @return
     */
    @Override
    public String filterType() {
        return POST_TYPE;
    }

    /**
     * filter 顺序
     *  这里在 SEND_RESPONSE_FILTER_ORDER (1000)之前返回
     * @return
     */
    @Override
    public int filterOrder() {
        return SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 主要业务逻辑写在这里
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletResponse response = requestContext.getResponse();
        /**
         * 向返回结果头(response 的 Header)中设置内容
         *  这里我们随便设置了一个 "X-Foo"，值是一个随机生成的 UUID
         */
        response.setHeader("X-Foo", UUID.randomUUID().toString());
        return null;
    }
}
