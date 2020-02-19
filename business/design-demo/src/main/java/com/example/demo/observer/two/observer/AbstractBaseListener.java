package com.example.demo.observer.two.observer;

import com.example.demo.observer.two.event.AbstractBaseEvent;

/**
 * @author 张杰
 * @date 2020/2/16 14:33
 */
public abstract class AbstractBaseListener {

     /**
      * 事件触发抽象类
      * @param event 事件
      * @author 张杰
      * @date 2020/2/16 14:35
      */
    public abstract void onEvent(AbstractBaseEvent event);
}
