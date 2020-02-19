package com.example.demo.observer.two.test;

import com.example.demo.observer.two.event.OrderEvent;
import com.example.demo.observer.two.observer.OrderOneListener;
import com.example.demo.observer.two.observer.OrderThreeListener;
import com.example.demo.observer.two.observer.OrderTwoListener;
import com.example.demo.observer.two.subject.BaseSubject;

import java.util.EventObject;

/**
 * @author 张杰
 * @date 2020/2/16 14:51
 */
public class Test {

    public static void main(String[] args) {
        BaseSubject baseSubject = new BaseSubject();
        baseSubject.attach(new OrderOneListener());
        baseSubject.attach(new OrderThreeListener());
        baseSubject.attach(new OrderTwoListener());

        EventObject eventObject = new EventObject("test001");

        baseSubject.notifyAllListeners(new OrderEvent(eventObject,1));
    }

}
