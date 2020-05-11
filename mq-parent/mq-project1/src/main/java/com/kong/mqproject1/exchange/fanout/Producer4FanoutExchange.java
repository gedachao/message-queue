package com.kong.mqproject1.exchange.fanout;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 19:17
 */
public class Producer4FanoutExchange {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();

        Channel channel = connection.createChannel();

        String exchangeName = "test_fanout_exchange";
        for (int i = 0; i < 10; i++) {
            String msg = "Hello rabbitmq fanout exchange:number:"+i;
            channel.basicPublish(exchangeName,"",null,msg.getBytes());

        }
        channel.close();
        connection.close();
    }
}
