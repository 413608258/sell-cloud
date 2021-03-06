//package com.lous.apigateway.filter;
//
//import com.lous.apigateway.constant.RedisConstant;
//import com.lous.apigateway.utils.CookieUtil;
//import com.netflix.zuul.ZuulFilter;
//import com.netflix.zuul.context.RequestContext;
//import com.netflix.zuul.exception.ZuulException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//
//import javax.servlet.http.Cookie;
//import javax.servlet.http.HttpServletRequest;
//
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_DECORATION_FILTER_ORDER;
//import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;
//
///**
// * @ClassName : AuthFilter
// * @Description : 权限校验, 区分买家和卖家
// *
// * @author : Loushuai
// * @since : 2019-01-28
// **/
//@Component
//public class AuthFilter extends ZuulFilter {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//    /**
//     * filter 类型
//     * 有一个常量类 FilterConstants, 这里做的是参数校验，用 PRE_TYPE
//     * @return
//     */
//    @Override
//    public String filterType() {
//        return PRE_TYPE;
//    }
//
//    /**
//     * filter 顺序
//     * 越小越靠前，这里我们放在 PRE_DECORATION_FILTER_ORDER (5)之前
//     * @return
//     */
//    @Override
//    public int filterOrder() {
//        return PRE_DECORATION_FILTER_ORDER - 1;
//    }
//
//    @Override
//    public boolean shouldFilter() {
//        return true;
//    }
//
//    /**
//     * 这里就是要实现的主要逻辑
//     * @return
//     * @throws ZuulException
//     */
//    @Override
//    public Object run() throws ZuulException {
//        RequestContext requestContext = RequestContext.getCurrentContext();
//        HttpServletRequest request = requestContext.getRequest();
//
//       /**
//        * /buyer/order/create 只能买家访问 (cookie 里有 openid)
//        * /buyer/order/finish 只能卖家访问 (cookie 里有 token, 并且对应的redis 中值)
//        * /buyer/product/list 都可以访问
//        */
//        //只能买家访问
//        if ("/buyer/order/create".equals(request.getRequestURI())) {
//            Cookie cookie = CookieUtil.get(request, "openid");
//            if (cookie == null || StringUtils.isEmpty(cookie.getValue())) {
//                requestContext.setSendZuulResponse(false);
//                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//        //只能卖家访问
//        if ("/buyer/order/finish".equals(request.getRequestURI())) {
//            Cookie cookie = CookieUtil.get(request, "token");
//            if (cookie == null) {
//                requestContext.setSendZuulResponse(false);
//                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }else if (StringUtils.isEmpty(cookie.getValue()) ||
//                    StringUtils.isEmpty(stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX, cookie.getValue())))) {
//                requestContext.setSendZuulResponse(false);
//                requestContext.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
//            }
//        }
//
//        return null;
//    }
//}
