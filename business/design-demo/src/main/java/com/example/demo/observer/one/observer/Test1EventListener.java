package com.example.demo.observer.one.observer;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.observer.one.event.MyEvent;
import org.junit.Test;

import java.util.EventObject;

/**
 * 事件监听器1
 * @author 张杰
 * @date 2020/2/16 12:09
 */
public class Test1EventListener extends AbstractEventListener {

    public Test1EventListener(int order){
        super(order);
    }

    @Override
    public void onTestEvent(MyEvent myEvent){
        System.out.println("事件监听器1开始执行");

        //获取事件持有的对象
        EventObject eventObject = myEvent.getEventObject();
        System.out.println(JSONObject.toJSONString(eventObject));
    }

}
