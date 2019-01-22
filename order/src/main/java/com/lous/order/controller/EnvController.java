package com.lous.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName : EnvController
 * @Description : TODO
 *
 * @author : Loushuai
 * @since : 2019-01-19
 **/
 
@RestController
@RequestMapping("/env")
public class EnvController {

    @Value("${env}")
    private String env;
    @Value("${eureka.client.serviceUrl.defaultZone}")
    private String enreka;

    @GetMapping("/print")
    public String print(){
        System.out.println("enreka = " + enreka);
        return env;
    }

}
