package com.lous.demo.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @ClassName : StreamClient
 * @Description :
 *
 * @author : Loushuai
 * @since : 2019-01-23
 **/
 
public interface StreamClient {

    String INPUT = "inputMessage";
    String OUTPUT = "outputMessage";

    String INPUT2 = "inputMessage2";
    String OUTPUT2 = "outputMessage2";

    @Input(StreamClient.INPUT)
    SubscribableChannel input();

    @Output(StreamClient.OUTPUT)
    MessageChannel output();

    @Input(StreamClient.INPUT2)
    SubscribableChannel input2();

    @Output(StreamClient.OUTPUT2)
    MessageChannel output2();
}
