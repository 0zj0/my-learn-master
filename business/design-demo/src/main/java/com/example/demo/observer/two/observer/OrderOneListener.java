package com.example.demo.observer.two.observer;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.observer.two.event.AbstractBaseEvent;
import com.example.demo.observer.two.event.OrderEvent;

import java.util.EventObject;

/**
 * @author 张杰
 * @date 2020/2/16 14:37
 */
public class OrderOneListener extends AbstractBaseListener {


     /**
      * 订单事件触发1
      * @param event 事件封装数据
      * @return void
      * @author 张杰
      * @date 2020/2/16 14:37
      */
    @Override
    public void onEvent(AbstractBaseEvent event) {
        System.out.println("订单事件监听器1执行");
        OrderEvent orderEvent = (OrderEvent) event;

        EventObject eventObject = orderEvent.getEventObject();
        System.out.println(JSONObject.toJSONString(eventObject.getSource()));
        System.out.println(orderEvent.getOrderId());
        orderEvent.setOrderId(orderEvent.getOrderId()+1);
    }
}
