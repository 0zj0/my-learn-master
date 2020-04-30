package com.example.demo.order;

/**
 * @author zhangjie
 * @date 2020/4/30 11:25
 */
public class OrderDemo {

    /**
     * 订单id
     */
    private long orderId;
    /**
     * 订单描述
     */
    private String desc;

    public OrderDemo(){}

    public OrderDemo(long orderId, String desc) {
        this.orderId = orderId;
        this.desc = desc;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "OrderDemo{" +
                "orderId=" + orderId +
                ", desc='" + desc + '\'' +
                '}';
    }
}
