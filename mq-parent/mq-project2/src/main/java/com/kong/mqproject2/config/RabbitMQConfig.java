package com.kong.mqproject2.config;

import com.kong.mqproject2.convert.TextMessageConvert;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;


/**
 * @author gedachao
 * @description
 * @date 2020/5/15 9:27
 */
@Configuration
@ComponentScan("com.kong.mqproject2.*")
public class RabbitMQConfig {

    @Bean
    public ConnectionFactory connectionFactory() throws IOException {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        InputStream inputStream = RabbitMQConfig.class.getResourceAsStream("/mq/mq.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        connectionFactory.setAddresses(properties.getProperty("mq.host"));
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;

    }

    @Bean
    public TopicExchange exchange001(){
        return new TopicExchange("exchange001",false,false);
    }

    @Bean
    public DirectExchange exchange002(){
        return new DirectExchange("exchange002",false,false);
    }

    @Bean
    public Queue queue001(){
        return new Queue("queue001",false);
    }

    @Bean
    public Queue queue002(){
        return new Queue("queue002",false);
    }

    @Bean
    public Binding binding001(){
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.#");
    }
    @Bean
    public Binding binding002(){
        return BindingBuilder.bind(queue002()).to(exchange001()).with("rabbitmq.#");
    }

    @Bean
    public Binding binding003(){
        return BindingBuilder.bind(queue001()).to(exchange002()).with("bing3.#");
    }

    @Bean
    public SimpleMessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue001(),queue002());
        container.setConcurrentConsumers(1);
        container.setMaxConcurrentConsumers(5);
        container.setDefaultRequeueRejected(false);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String s) {
                return s+"_"+ UUID.randomUUID().toString();
            }
        });

    /**
     * //设置消息监听器的方式1
     * container.setMessageListener(new ChannelAwareMessageListener() {
     *             @Override
     *             public void onMessage(Message message, Channel channel) throws Exception {
     *                 String str  = new String(message.getBody());
     *                 System.out.println("消费者："+str);
     *             }
     *         });
     */



        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        //自定义监听方法，默认的监听方法为handleMessage
        adapter.setDefaultListenerMethod("consumeMessageString");

        /**
         *         //设置转化器1Json
         *         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
         *         adapter.setMessageConverter(jackson2JsonMessageConverter);
         *         //需要在发送端设置messageProperties.setContentType("application/json");
         */


        /**
         *         //设置转换器2,Java的object类型转换
         *         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
         *         DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
         *         jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
         *         adapter.setMessageConverter(jackson2JsonMessageConverter);
         *         //需要在发送端设置：messageProperties.getHeaders().put("__TypeId__","com.kong.your.class");
         */

        /**
         *         //Json转为Java时，将Json中的指定标签转为指定Java类
         *         Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
         *         DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
         *         HashMap<String, Class<?>> idClassMapping = new HashMap<String, Class<?>>();
         *         idClassMapping.put("yourClassName1",com.kong.YourClass1.class);
         *         idClassMapping.put("yourClassName2",com.kong.YourClass2.class);
         *         javaTypeMapper.setIdClassMapping(idClassMapping);
         *         jackson2JsonMessageConverter.setJavaTypeMapper(javaTypeMapper);
         *         adapter.setMessageConverter(jackson2JsonMessageConverter);
         *         //此时需要再发送端指定messageProperties.getHeaders.put("__TypeId__","yourSimpleClassName");
         *         //只需指定简单类名即可,不需要指定全路径
         *
         */




        //设置转换器
        adapter.setMessageConverter(new TextMessageConvert());


        /**
         *          Map<String,String> queueOrTagToMethodName = new HashMap<>();
         *          //将queue和MessageDelegate中的方法(method001,method002)绑定，即指定的queue的消息发送至指定的方法中
         *          queueOrTagToMethodName.put("queue001","method001");
         *          queueOrTagToMethodName.put("queue001","method001");
         *           adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
         */
        container.setMessageListener(adapter);
        return container;


    }


}
