package com.kong.mqproject1.topic;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 18:42
 */
public class Consumer4TopicExchange {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();

        //声明
        String exchangeName = "test_topic_exchange";
        String exchangeType = "topic";
        String queueName = "test_topic_queue";
        String routingKey = "user.*";

        //声明交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        //声明队列
        channel.queueDeclare(queueName,false,false,false,null);
        //绑定
        channel.queueBind(queueName,exchangeName,routingKey);

        QueueingConsumer consumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,consumer);

        //循环获取消息
        while(true){
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息："+msg);

        }
    }

}
