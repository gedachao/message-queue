package com.kong.mqproject1.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * @author gedachao
 * @description
 * @date 2020/5/14 10:39
 */
public class MyConsume extends DefaultConsumer {
    public MyConsume(Channel channel) {
        super(channel);
    }


    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("--------------consume message--------------");
        System.out.println("consumerTag:"+consumerTag);
        System.out.println("envelope:"+envelope);
        System.out.println("AMQP.BasicProperties:"+properties);
        System.out.println("body:"+new String(body));
    }
}
