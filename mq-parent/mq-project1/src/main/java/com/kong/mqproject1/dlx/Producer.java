package com.kong.mqproject1.dlx;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author gedachao
 * @description
 * @date 2020/5/14 10:02
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.save";
        String msg = "Hello,Rabbitmq dlx message";
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)
                .contentEncoding("UTF-8")
                .expiration("10000")
                .build();

        for (int i = 0; i < 5; i++) {
            channel.basicPublish(exchangeName,routingKey,true,properties,msg.getBytes());
        }
    }

}
