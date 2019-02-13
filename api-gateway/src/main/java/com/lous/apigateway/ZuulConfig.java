package com.lous.apigateway;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.stereotype.Component;

/**
 * @ClassName : ZuulConfig
 * @Description : 结合 Spring Cloud Config 和 Spring Cloud Bus 实现配置的动态刷新（即：动态路由）
 *
 * @author : Loushuai
 * @since : 2019-01-28
 **/
@Component
public class ZuulConfig {
    @ConfigurationProperties("zuul")
    @RefreshScope
    public ZuulProperties zuulProperties() {
        return new ZuulProperties();
    }
}
