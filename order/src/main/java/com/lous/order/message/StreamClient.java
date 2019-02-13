/*
package com.lous.order.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

*/
/**
 * @ClassName : StreamClient
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-23
 **//*

 
public interface StreamClient {

    String INPUT = "inputMessage";
    String OUTPUT = "outputMessage";

    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.OUTPUT)
    MessageChannel output();
}
*/
