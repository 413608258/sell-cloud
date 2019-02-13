package com.lous.demo.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * @ClassName : StreamReceiver
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-23
 **/
@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    @StreamListener(StreamClient.INPUT)
    //SendTo注解将返回信息，发送消息给OUTPUT2管道
    @SendTo(StreamClient.OUTPUT2)
    public String process(String message){
        log.info("StreamReceiver: {}", message);
        return "received...";
    }

    @StreamListener(StreamClient.INPUT2)
    public void process2(String message){
        log.info("StreamReceiver2: {}", message);
    }

}
