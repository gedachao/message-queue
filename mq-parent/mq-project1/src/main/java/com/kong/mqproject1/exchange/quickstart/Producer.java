package com.kong.mqproject1.exchange.quickstart;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 13:55
 */
public class Producer {
    public static void main(String[] args) throws Exception{
        /*//1. 创建一个ConnectionFactory,配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.43.230");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");*/

        //2. 通过连接工厂创建连接
        Connection connection = MqFactory.getConnection();

        //3.通过connection创建一个Channel
        Channel channel = connection.createChannel();

        //4. 通过Channel发送数据
        for (int i = 0; i < 5; i++) {
            String msg = "Hello,mq";
            channel.basicPublish("","test001",null,msg.getBytes());
        }

        //5.关闭连接
        channel.close();
        connection.close();
    }

}
