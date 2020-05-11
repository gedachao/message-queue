package com.kong.mqproject1.exchange.fanout;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 19:16
 */
public class Consumer4FanoutExchange {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_fanout_exchange";
        String exchangeType = "fanout";
        String queueName = "test_fanout_queue";
        String otherQueue = "test_fanout_other_queue";
        //不设置路由键
        String routingKey = "";
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,null);
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueDeclare(otherQueue,false,false,false,null);
        channel.queueBind(queueName,exchangeName,routingKey);
        channel.queueBind(otherQueue,exchangeName,"user.*");
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,queueingConsumer);
        channel.basicConsume(otherQueue,true,queueingConsumer);

        while(true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println("收到消息："+msg);
        }


    }
}
