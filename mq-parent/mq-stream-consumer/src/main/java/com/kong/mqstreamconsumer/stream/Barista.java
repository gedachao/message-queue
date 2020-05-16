package com.kong.mqstreamconsumer.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author gedachao
 * @description
 * @date 2020/5/16 16:59
 */
public interface Barista {
    String INPUT_CHANNEL = "input_channel";
    @Input(Barista.INPUT_CHANNEL)
    SubscribableChannel loginput();
}
