package com.kong.mqproject1.commonTools;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 14:29
 */
public class MqFactory {
    private final static String url = "192.168.43.230";
    public static ConnectionFactory getConnectFactory(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(url);
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    public static Connection getConnection() throws Exception{
        Connection connection = getConnectFactory().newConnection();
        return connection;
    }
}
