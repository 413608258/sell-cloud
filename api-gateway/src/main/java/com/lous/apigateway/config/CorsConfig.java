package com.lous.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * @ClassName : CorsConfig
 * @Description : 跨域配置
 *
 * @author : Loushuai
 * @since : 2019-01-29
 * Cors(跨域资源共享):
 *      C-  Cross
 *      O-  Origin
 *      R-  Resource
 *      S-  Sharing
 **/
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();

        //是否支持 Cookie 跨域
        config.setAllowCredentials(true);
        //要放置哪些原始域，是一个List， 域 例子：http:www.a.com
        config.setAllowedOrigins(Arrays.asList("*"));
        //允许的头
        config.setAllowedHeaders(Arrays.asList("*"));
        //请求方式 GET/POST
        config.setAllowedMethods(Arrays.asList("*"));
        //缓存时间 例如 300秒
        config.setMaxAge(Long.valueOf(300));

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

}
