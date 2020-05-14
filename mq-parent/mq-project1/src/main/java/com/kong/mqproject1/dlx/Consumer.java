package com.kong.mqproject1.dlx;

import com.kong.mqproject1.commonTools.MqFactory;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gedachao
 * @description 自定义消费端
 * @date 2020/5/14 10:03
 */
public class Consumer {
    public static void main(String[] args) throws Exception {
        Connection connection = MqFactory.getConnection();
        Channel channel = connection.createChannel();
        String exchangeName = "test_dlx_exchange";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";

        Map<String,Object> arguments = new HashMap<>();
        //设置参数
        arguments.put("x-dead-letter-exchange","dlx.exchange");
        channel.exchangeDeclare(exchangeName,"topic",true,false,null);
        //在对此队列声明是要添加其中的argument参数
        channel.queueDeclare(queueName,true,false,false,arguments);
        channel.queueBind(queueName,exchangeName,routingKey);


        //声明死信交换机,队列,及绑定
        channel.exchangeDeclare("dlx.exchange","topic",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");



        channel.basicConsume(queueName,true,new MyConsume(channel));


    }

}
