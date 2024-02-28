package com.yupi.springbootinit.bizmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class MyMessageProducer {
    @Resource
    private RabbitTemplate rabbitTemplate;

    public void  sendMessage(String exchange,String routeKey,String message){
        rabbitTemplate.convertAndSend(exchange,routeKey,message);
    }
}
