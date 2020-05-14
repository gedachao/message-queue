package com.kong.mqproject1.limit;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author gedachao
 * @description  消费端的限流策略qos
 * @date 2020/5/14 10:03
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_qos_exchange";
        String routingKey = "qos.#";
        String queueName = "test_qos_queue";

        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);

        //限流方式,将autoAck设置为false
        channel.basicQos(0,1,false);
        channel.basicConsume(queueName,false,new MyConsume(channel));
    }

}
