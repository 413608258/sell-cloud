/*
package com.lous.order.controller;

import com.lous.order.message.StreamClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

*/
/**
 * @ClassName : SendMessageController
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-23
 **//*

@RestController
public class SendMessageController {

    @Autowired
    private StreamClient streamClient;

    @GetMapping("/sendMessage")
    public void process() {
        String message = "now" + new Date();
        streamClient.output().send(MessageBuilder.withPayload(message).build());
    }

}
*/
