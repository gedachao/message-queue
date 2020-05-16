package com.kong.mqsbootconsumer;

import com.kong.mqsbootconsumer.entity.Order;
import com.kong.mqsbootconsumer.producer.RabbitSender;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MqSbootProducerApplicationTests {
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    @Autowired
    private RabbitSender rabbitSender;


    @Test
    void contextLoads() {
    }


    @Test
    public void testSender1() throws Exception{
        Map<String,Object> properties = new HashMap<>();
        properties.put("number",12345);
        properties.put("send_time",simpleDateFormat.format(new Date()));
        rabbitSender.send("Hello,RabbitMQ For Spring Boot",properties);
    }

    @Test
    public void testSender2() throws Exception{
        Order order = new Order("001", "第一个订单");
        rabbitSender.sendOrder(order);
    }

}
