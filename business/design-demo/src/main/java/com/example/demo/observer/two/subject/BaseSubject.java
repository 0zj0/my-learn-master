package com.example.demo.observer.two.subject;

import com.example.demo.observer.two.event.AbstractBaseEvent;
import com.example.demo.observer.two.observer.AbstractBaseListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础主题类
 * @author 张杰
 * @date 2020/2/16 14:32
 */
public class BaseSubject {

    private List<AbstractBaseListener> listeners = new ArrayList<>();

    /**
     * 将监听事件存放到广播列表中
     * @param listener
     */
    public void attach(AbstractBaseListener listener){
        listeners.add(listener);
    }

    /**
     * 循环执行事件监听处理
     * @param event
     */
    public void notifyAllListeners(AbstractBaseEvent event){
        for (AbstractBaseListener listener : listeners) {
            listener.onEvent(event);
        }
    }
}
