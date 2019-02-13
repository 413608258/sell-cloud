package com.lous.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.lous.apigateway.exception.RateLimitException;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.SERVLET_DETECTION_FILTER_ORDER;

/**
 * @ClassName : RateFilter
 * @Description : Zuul 限流, 令牌桶限流(已经有一个开源组件，google 开源的)
 *
 * @author : Loushuai
 * @since : 2019-01-28
 **/
@Component
public class RateLimiterFilter extends ZuulFilter {
    //定义速率
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * filter 顺序
     * 限流要放在最前面位置来做，
     * 我们要放在最小的 SERVLET_DETECTION_FILTER_ORDER (-3)之前
     * @return
     */
    @Override
    public int filterOrder() {
        return SERVLET_DETECTION_FILTER_ORDER -1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 主要业务逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        /**
         * 如果没有拿到令牌
         */
        if (!RATE_LIMITER.tryAcquire()) {
            /*
            //返回方式一
            requestContext.setSendZuulResponse(false);
            //设置状态码, 这里返回的是401, 不确定是否规范
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            */
            //返回方式二
            throw new RateLimitException();
        }
        return null;
    }
}
