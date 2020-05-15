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
}
