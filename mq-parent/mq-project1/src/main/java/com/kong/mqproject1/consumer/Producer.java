package com.kong.mqproject1.consumer;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

/**
 * @author gedachao
 * @description
 * @date 2020/5/14 10:02
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_custom_consumer_exchange";
        String routingKey = "consumer.save";
        String msg = "Hello,Rabbitmq custom consumer";


        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());
        }
    }

}
