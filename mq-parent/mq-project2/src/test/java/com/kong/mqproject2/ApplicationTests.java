package com.kong.mqproject2;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void test1() throws Exception{
        RabbitTemplate rabbitTemplate = rabbitAdmin.getRabbitTemplate();

        //声明交换机
        rabbitAdmin.declareExchange(new DirectExchange("test.direct",false,false));
        rabbitAdmin.declareExchange(new TopicExchange("test.topic",false,false));
        rabbitAdmin.declareExchange(new FanoutExchange("test.fanout",false,false));

        //声明队列
        rabbitAdmin.declareQueue(new Queue("test_direct_queue",false));
        rabbitAdmin.declareQueue(new Queue("test_topic_queue",false));
        rabbitAdmin.declareQueue(new Queue("test_fanout_queue",false));


        //绑定方式1:队列和交换机前文已经声明
        rabbitAdmin.declareBinding(new Binding("test_direct_queue",
                Binding.DestinationType.QUEUE,
                "test_direct",
                "direct",
                new HashMap<>()));

        //绑定方式2:队列和交换机前文未声明
        rabbitAdmin.declareBinding(BindingBuilder
                .bind(new Queue("test_topic_queue", false))
                .to(new TopicExchange("test.topic", false, false))
                .with("user.#"));



    }

    @Test
    public void test02()throws Exception{
        System.out.println("hello");

    }

    @Test
    public void testSendMessage() throws Exception{
        String msg = "Hello,testSendMessage()";
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("desc","消息描述");
        messageProperties.getHeaders().put("type","自定义消息类型");
        Message message = new Message(msg.getBytes(), messageProperties);
        rabbitTemplate.convertAndSend("exchange001", "spring.test", message, new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                System.out.println("----------添加额外设置----------");
                message.getMessageProperties().getHeaders().put("desc","额外修改的信息描述");
                message.getMessageProperties().getHeaders().put("attr","额外新加的属性");
                return message;
            }
        });
        rabbitTemplate.send("exchange002","bing3.test",message);


    }


}
