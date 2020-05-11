package com.kong.mqproject1.message;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

import java.util.Map;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 13:54
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();

        String queueName = "test001";
        //声明一个队列
        channel.queueDeclare(queueName,true,false,false,null);

        //创建一个消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //设置Channel
        channel.basicConsume(queueName,true,queueingConsumer);

        //获取消息
        while(true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String s = new String(delivery.getBody());
            Map<String, Object> headers = delivery.getProperties().getHeaders();
            System.out.println(headers.get("mine"));
            System.err.println("消费端："+s);

        }




    }
}
