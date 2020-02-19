package com.example.demo.observer.one.event;

import java.util.EventObject;

/**
 * 订单回调事件，用于定义具体的事件
 * 订单回调事件
 * @author 张杰
 * @date 2020/2/16 12:01
 */
public class OrderCallbackEvent extends AbstractMyEvent {


    public OrderCallbackEvent(EventObject eventObject) {
        super(eventObject);
    }
}
