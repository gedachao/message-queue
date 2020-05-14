package com.kong.mqproject1.ack;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author gedachao
 * @description 消费端ack与重回队列
 * @date 2020/5/14 10:03
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.#";
        String queueName = "test_ack_queue";

        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        channel.basicConsume(queueName,false,new MyConsume(channel));


    }

}
