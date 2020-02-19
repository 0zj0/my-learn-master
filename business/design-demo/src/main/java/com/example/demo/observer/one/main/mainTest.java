package com.example.demo.observer.one.main;

import com.example.demo.observer.one.event.OrderCallbackEvent;
import com.example.demo.observer.one.observer.AbstractEventListener;
import com.example.demo.observer.one.observer.Test1EventListener;
import com.example.demo.observer.one.observer.Test2EventListener;
import com.example.demo.observer.one.observer.Test3EventListener;
import com.example.demo.observer.one.subject.OrderEventMulticaster;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EventObject;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 张杰
 * @date 2020/2/16 12:23
 */
public class mainTest {

    public static void main(String[] args) {

        //定义三个监听器
        List<AbstractEventListener> listeners = new ArrayList<>();
        listeners.add(new Test1EventListener(1));
        listeners.add(new Test3EventListener(2));
        listeners.add(new Test2EventListener(3));

        //根据order排序
        listeners = listeners.stream().sorted(Comparator.comparing(AbstractEventListener::getOrder)).collect(Collectors.toList());

        //定义广播器
        OrderEventMulticaster orderEventMulticaster = new OrderEventMulticaster(listeners);

        //定义EventObject
        Order order = new Order("wx"+System.currentTimeMillis());
        EventObject eventObject = new EventObject(order);

        //广播事件
        orderEventMulticaster.multicastEvent(new OrderCallbackEvent(eventObject));
    }

}
