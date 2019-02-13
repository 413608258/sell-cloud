package com.lous.order.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @ClassName : MqReceiver
 * @Description : 接收 MQ 消息
 *
 * @author : Loushuai
 * @since : 2019-01-22
 **/
@Slf4j
@Component
public class MqReceiver {

    //1.@RabbitListener(queues = "myQueue")

    //2.自动创建队列
    //@RabbitListener(queuesToDeclare = @Queue("myQueue"))

    //3.自动创建，Exchange 和 Queue绑定
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("myQueue"),
            exchange = @Exchange("myExchange")
    ))
    public void process(String message){
        log.info("MqReceiver: {}", message);
    }

    /*
    * 数码供应商服务 接收消息
    * */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "computer",
            value = @Queue("computerOrder")
    ))
    public void processComputer(String message){
        log.info("Computer MQReceiver: {}", message);
    }
    /*
    * 数码供应商服务 接收消息
    * */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("myOrder"),
            key = "fruit",
            value = @Queue("fruitOrder")
    ))
    public void processFruit(String message){
        log.info("Fruit MQReceiver: {}", message);
    }
}
