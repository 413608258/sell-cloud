package com.lous.order.message;

import com.lous.order.OrderApplicationTests;
import org.junit.Test;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static org.junit.Assert.*;

@Component
public class MqReceiverTest extends OrderApplicationTests {

    @Autowired
    private AmqpTemplate amqpTemplate;
    /*
    * 发送 MQ 消息测试
    * */
    @Test
    public void send() {
        amqpTemplate.convertAndSend("myQueue", "now " + new Date());
    }

    @Test
    public void sendOrder() {
        amqpTemplate.convertAndSend("myOrder", "computer", "now " + new Date());
    }
    @Test
    public void sendOrder2() {
        amqpTemplate.convertAndSend("myOrder", "fruit", "now " + new Date());
    }
}