package com.kong.mqstreamconsumer.receiver;

import com.kong.mqstreamconsumer.stream.Barista;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@EnableBinding(Barista.class)
@Service
@Slf4j
public class RabbitMqReceiver {
    public void receiver(Message message) throws Exception{
        Channel channel = (Channel) message.getHeaders().get(AmqpHeaders.CHANNEL);
        Long deliveryTag = (Long) message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        log.info("input stream1接受数据：{}",message);
        log.info("消费完毕");
        channel.basicAck(deliveryTag,false);


    }



}
