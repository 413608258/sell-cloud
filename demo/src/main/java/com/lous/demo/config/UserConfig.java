package com.lous.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * @ClassName : UserConfig
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-22
 **/
@Component
@ConfigurationProperties("user")
@RefreshScope
public class UserConfig {
    private String name;
    private String pass;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
