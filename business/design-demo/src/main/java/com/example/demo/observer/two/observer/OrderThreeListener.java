package com.example.demo.observer.two.observer;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.observer.two.event.AbstractBaseEvent;
import com.example.demo.observer.two.event.OrderEvent;

import java.util.EventObject;

/**
 * @author 张杰
 * @date 2020/2/16 14:37
 */
public class OrderThreeListener extends AbstractBaseListener {


     /**
      * 订单事件触发3
      * @param event 事件封装数据
      * @return void
      * @author 张杰
      * @date 2020/2/16 14:37
      */
    @Override
    public void onEvent(AbstractBaseEvent event) {
        System.out.println("订单事件监听器3执行");

        EventObject eventObject = event.getEventObject();
        System.out.println(JSONObject.toJSONString(eventObject.getSource()));
        eventObject = new EventObject("test003");
        event.setEventObject(eventObject);

    }
}
