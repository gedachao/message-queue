package com.kong.mqstreamproducer.sender;

import com.kong.mqstreamproducer.stream.Barista;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author gedachao
 * @description
 * @date 2020/5/16 16:23
 */
@EnableBinding(Barista.class)
@Service
@Slf4j
public class RabbitmqSender {
    @Autowired
    private Barista barista;

    //发送消息
    public String sendMessage(Object message, Map<String,Object> properties){
        try {
            MessageHeaders messageHeaders = new MessageHeaders(properties);
            Message<Object> msg = MessageBuilder.createMessage(message, messageHeaders);
            boolean sendStatus = barista.logoutput().send(msg);
            System.err.println("-----------sending-------------");
            log.info("发送数据：{},sendStatus:{}",message,sendStatus);
        } catch (Exception e) {
            System.err.println("----------error----------");
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
        return null;

    }

}
