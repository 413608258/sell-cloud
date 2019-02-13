/*
package com.lous.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

*/
/**
 * @ClassName : StreamReceiver
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-23
 **//*

@Component
@EnableBinding(StreamClient.class)
@Slf4j
public class StreamReceiver {

    @StreamListener(StreamClient.INPUT)
    public void process(Object message){
        log.info("StreamReceiver: {}", message);
    }

}
*/
