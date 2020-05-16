package com.kong.mqstreamproducer.stream;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

/**
 * @author gedachao
 * @description
 * @date 2020/5/16 15:54
 */
public interface Barista {

    String OUTPUT_CHANNEL = "output_channel";

    @Output(Barista.OUTPUT_CHANNEL)
    MessageChannel logoutput();

}
