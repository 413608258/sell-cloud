package com.lous.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName :
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-19
 **/
 
@RestController
@RequestMapping("/user")
@RefreshScope
public class EnvController {

    @Value("${user.name}")
    private String name;

    @GetMapping("/print")
    public String print(){
        System.out.println("name = " + name);
        return name;
    }

}
