package com.kong.mqproject1.topic;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 18:43
 */
public class Producer4TopicExchange {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();

        //声明
        String exchangeName = "test_topic_exchange";
        String routingKey1 = "user.save";
        String routingKey2 = "user.update";
        String routingKey3 = "user.delete.abc";

        //发送
        String msg = "Hello rabbitmq with 4 topic exchange:";
        channel.basicPublish(exchangeName,routingKey1,null,(msg+routingKey1).getBytes());
        channel.basicPublish(exchangeName,routingKey2,null,(msg+routingKey2).getBytes());
        channel.basicPublish(exchangeName,routingKey3,null,(msg+routingKey3).getBytes());

        channel.close();
        connection.close();

    }
}
