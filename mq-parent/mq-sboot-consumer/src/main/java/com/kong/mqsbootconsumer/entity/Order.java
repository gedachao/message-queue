package com.kong.mqsbootconsumer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author gedachao
 * @description
 * @date 2020/5/16 13:51
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order implements Serializable {

    private static final long serialVersionUID = -4064503570845334300L;
    private String id;
    private String name;
}
