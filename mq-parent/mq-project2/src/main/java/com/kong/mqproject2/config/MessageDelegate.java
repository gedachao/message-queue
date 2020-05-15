package com.kong.mqproject2.config;

/**
 * @author gedachao
 * @description
 * @date 2020/5/15 16:21
 */
public class MessageDelegate {
    public void handleMessage(byte[] message){
        System.out.println("默认方法，消息内容："+new String(message));
    }

    public void consumeMessage(byte[] message){
        System.out.println("自定义方法consumeMessage(),消息内容:"+new String(message));
    }

    public void consumeMessageString(String message){
        System.out.println("自定义方法consumeMessageString(),消息内容:"+message);
    }
}
