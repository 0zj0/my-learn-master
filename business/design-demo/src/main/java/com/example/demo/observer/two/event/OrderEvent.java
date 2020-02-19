package com.example.demo.observer.two.event;

import java.util.EventObject;

/**
 * 订单 具体数据
 * @author 张杰
 * @date 2020/2/16 14:23
 */
public class OrderEvent extends  AbstractBaseEvent {


    private int orderId;

    /**
     * @param eventObject 数据对象(上下文不变)
     */
    public OrderEvent(EventObject eventObject) {
        super(eventObject);
    }

    public OrderEvent(EventObject eventObject, int orderId) {
        super(eventObject);
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
