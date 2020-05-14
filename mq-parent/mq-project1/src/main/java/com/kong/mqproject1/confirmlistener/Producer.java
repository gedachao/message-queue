package com.kong.mqproject1.confirmlistener;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;

/**
 * @author gedachao
 * @description
 * @date 2020/5/14 9:08
 */
public class Producer {

    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();

        //指定消息投递模式：消息的确认模式
        channel.confirmSelect();

        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        //发送消息
        String msg = "Hello Rabbitmq confirm message!";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

        //添加一个确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("---------------act:"+deliveryTag+"-------------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("---------------no act:"+deliveryTag+"-----------");
            }
        });



    }

}
