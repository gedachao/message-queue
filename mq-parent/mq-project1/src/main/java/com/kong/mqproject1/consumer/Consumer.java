package com.kong.mqproject1.consumer;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author gedachao
 * @description 自定义消费端
 * @date 2020/5/14 10:03
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_custom_consumer_exchange";
        String routingKey = "consumer.#";
        String queueName = "test_consumer_queue";

        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,true,new MyConsume(channel));


    }

}
