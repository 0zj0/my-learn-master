package com.example.demo.observer.two.event;

import java.util.EventObject;

/**
 * 事件的接口类，可以设置或获取数据EventObject
 * @author 张杰
 * @date 2020/2/16 14:16
 */
public abstract class AbstractBaseEvent {

    /**
     * 数据对象
     */
    private EventObject eventObject;

    public AbstractBaseEvent(EventObject eventObject) {
        this.eventObject = eventObject;
    }

    public EventObject getEventObject() {
        return eventObject;
    }

    public void setEventObject(EventObject eventObject) {
        this.eventObject = eventObject;
    }
}
