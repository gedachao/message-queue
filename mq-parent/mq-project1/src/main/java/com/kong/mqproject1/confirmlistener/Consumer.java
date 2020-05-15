package com.kong.mqproject1.confirmlistener;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author gedachao
 * @description
 * @date 2020/5/14 9:08
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        //指定消息投递模式：消息的确认模式
        String exchange = "test_confirm_exchange";
        String routingKey = "confirm.#";
        String queueName = "test_confirm_queue";
        channel.exchangeDeclare(exchange,"topic",true);
        channel.queueDeclare(queueName,true,false,false,null);
        channel.queueBind(queueName,exchange,routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println(msg);
        }



    }
}
