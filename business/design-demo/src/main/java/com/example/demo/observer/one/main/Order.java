package com.example.demo.observer.one.main;

/**
 * @author 张杰
 * @date 2020/2/16 12:28
 */
public class Order {

    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Order(String orderNo){
        this.orderNo = orderNo;
    }

}
