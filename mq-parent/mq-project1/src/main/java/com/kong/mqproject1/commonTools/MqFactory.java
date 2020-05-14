package com.kong.mqproject1.commonTools;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author gedachao
 * @description
 * @date 2020/5/9 14:29
 */
public class MqFactory {
    private final static String resUrl = "mq/mq.properties";
    public static ConnectionFactory getConnectFactory(){
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Properties properties = new Properties();
        InputStream inputStream = MqFactory.class.getClassLoader().getResourceAsStream(resUrl);
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            System.out.println("mq|mq-home.properties not found!");
            e.printStackTrace();
        }
        System.out.println("host为"+properties.getProperty("mq.host"));
        connectionFactory.setHost(properties.getProperty("mq.host"));
        connectionFactory.setPort(Integer.valueOf(properties.getProperty("mq.port")));
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    public static Connection getConnection() throws Exception{
        Connection connection = getConnectFactory().newConnection();
        return connection;
    }
}
