package com.lous.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * @ClassName : TokenFilter
 * @Description : Zuul: 权限统一验证
 *
 * @author : Loushuai
 * @since : 2019-01-28
 **/
@Component
public class TokenFilter extends ZuulFilter {
    /**
     * filter 类型
     * 有一个常量类 FilterConstants, 这里做的是参数校验，用 PRE_TYPE
     * @return
     */
    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    /**
     * filter 顺序
     * 越小越靠前，这里我们放在 PRE_DECORATION_FILTER_ORDER (5)之前
     * @return
     */
    @Override
    public int filterOrder() {
        return PRE_DECORATION_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 这里就是要实现的主要逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();

        //这里从 url 参数里获取，也可以从 cookie, header 里获取
        String token = request.getParameter("token");
        /**
         * token 校验, 这里只是校验其是否为空,
         * 在真实业务中可能校验其是否和 Redis中的相同
         */
        if (StringUtils.isEmpty(token)) {
            /*
            //暂时注释掉
            requestContext.setSendZuulResponse(false);
            //设置状态码：权限不足一般是 401
            requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
            */
        }
        return null;
    }
}
