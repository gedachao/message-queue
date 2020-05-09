package com.kong.mqproject1.direct;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 15:49
 */
public class DirectProducer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();

        //声明Exchange和routeKey
        String exchangeName = "test_direct_exchange";
        String routingKey = "test.direct";

        String msg = "[Direct] Hello RabbitMQ!";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes("utf-8"));
        channel.close();
        connection.close();

    }
}
