package com.tensquare.rabbit.comsumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RabbitListener(queues = {"zhuo"})
@Component
public class Comsumer1 {
    @RabbitHandler
    public void getMsg(String msg) {
        System.out.println("testMsg:" + msg);
    }
}
