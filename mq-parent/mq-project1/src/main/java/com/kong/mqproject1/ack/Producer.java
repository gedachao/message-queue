package com.kong.mqproject1.ack;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;

import java.util.HashMap;

/**
 * @author gedachao
 * @description
 * @date 2020/5/14 10:02
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.save";

        for (int i = 0; i < 5; i++) {
            HashMap<String, Object> headerMap = new HashMap<>();
            headerMap.put("num",i);
            String msg = "Hello,Rabbitmq ack"+i;
            AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                    .deliveryMode(2)
                    .contentEncoding("utf-8")
                    .headers(headerMap)
                    .build();
            channel.basicPublish(exchangeName,routingKey,true,properties,msg.getBytes());
        }
    }

}
