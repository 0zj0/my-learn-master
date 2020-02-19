package com.example.demo.observer.one.subject;

import com.example.demo.observer.one.event.MyEvent;
import com.example.demo.observer.one.observer.AbstractEventListener;

import java.util.List;

/**
 * 事件广播器
 * @author 张杰
 * @date 2020/2/16 12:16
 */
public class OrderEventMulticaster {

    private List<AbstractEventListener> listeners;

    public OrderEventMulticaster(List<AbstractEventListener> listeners){
        this.listeners = listeners;
    }

    public void multicastEvent(MyEvent myEvent){
        for(AbstractEventListener eventListener : listeners){
            eventListener.onTestEvent(myEvent);
        }
    }

}
