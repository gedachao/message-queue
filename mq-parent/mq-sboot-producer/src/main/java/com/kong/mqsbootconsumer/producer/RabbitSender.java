package com.kong.mqsbootconsumer.producer;

import com.kong.mqsbootconsumer.entity.Order;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

/**
 * @author gedachao
 * @description
 * @date 2020/5/16 10:08
 */
@Component
public class RabbitSender {

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean b, String s) {
            System.out.println("correlationDate: "+ correlationData);
            System.out.println("ack: "+b);
            if(!b){
                System.out.println("开始异常处理.....");
            }
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("return: exchange:"+exchange+","+"routingKey:"+routingKey);
        }
    };

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Object message, Map<String,Object> properties){
        MessageHeaders  messageHeaders = new MessageHeaders(properties);
        Message<Object> message1 = MessageBuilder.createMessage(message, messageHeaders);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("exchange1","boot.hello",message1,correlationData);


    }

    public void sendOrder(Order order){
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend("exchange1","boot.hello",order,correlationData);
    }

}
