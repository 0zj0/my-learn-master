package com.example.demo.observer.one.observer;

import com.example.demo.observer.one.event.MyEvent;

/**
 * 监听器接口类，定义监听器的方法
 * @author 张杰
 * @date 2020/2/16 12:05
 */
public interface EventListener {

    void onTestEvent(MyEvent myEvent);
}
