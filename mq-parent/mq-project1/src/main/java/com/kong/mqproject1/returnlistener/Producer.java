package com.kong.mqproject1.returnlistener;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ReturnListener;

import java.io.IOException;

/**
 * @author gedachao
 * @description
 * @date 2020/5/14 10:02
 */
public class Producer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyError = "abc.save";
        String msg = "Hello,Rabbitmq returnListener";


        //***添加错误消息监听器
        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("-----handle return--------:");
                System.out.println("replyCode:"+replyCode);
                System.out.println("replyText:"+replyText);
                System.out.println("exchange:"+exchange);
                System.out.println("routingKey:"+routingKey);
                System.out.println("AMQP.BasicProperties:"+properties);
                System.out.println("body:"+new String(body));
            }
        });
        channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());
    }


}
