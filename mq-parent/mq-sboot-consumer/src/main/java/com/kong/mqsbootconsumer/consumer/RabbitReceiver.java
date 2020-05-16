package com.kong.mqsbootconsumer.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author gedachao
 * @description
 * @date 2020/5/16 13:31
 */
@Component
public class RabbitReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue1",durable = "true"),
            exchange = @Exchange(value = "exchange1",
                    durable = "true",
                    type = "topic",
                    ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    ))
    @RabbitHandler
    public void onMessage(Message message, Channel channel) throws Exception{
        System.err.println("-----------消费端-------------");
        System.out.println("消费端Payload"+message.getPayload());
        Long deliveryTag = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag,false);
    }



    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}",
                            durable = "${spring.rabbitmq.listener.order.queue.durable}"),
                    exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}",
                            durable = "${spring.rabbitmq.listener.order.exchange.durable}",
                            type = "${spring.rabbitmq.listener.order.exchange.type}",
                            ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
                    key = "${spring.rabbitmq.listener.order.key}"
            )
    )
    @RabbitHandler
    public void onOrderMessage(@Payload com.kong.mqsbootconsumer.entity.Order order,
                          Channel channel,
                          @Headers Map<String,Object> headers) throws Exception{
        System.err.println("-----------消费端-------------");
        System.out.println("消费端order"+order.getId());
        Long deliveryTag = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        //手工ACK
        channel.basicAck(deliveryTag,false);
    }
}
