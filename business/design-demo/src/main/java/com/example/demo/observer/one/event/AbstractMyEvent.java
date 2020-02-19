package com.example.demo.observer.one.event;

import java.util.EventObject;

/**
 * 事件抽象实现类，持有EventObject对象，并提供默认实现
 * 抽象事件
 * @author zhangjie
 * @date 2020/2/16 11:54
 */
public abstract class AbstractMyEvent implements MyEvent  {

    private long timestamp;

    private EventObject eventObject;

    public AbstractMyEvent(EventObject eventObject){
        this.setEventObject(eventObject);
    }

    @Override
    public EventObject getEventObject() {
        return eventObject;
    }

    @Override
    public void setEventObject(EventObject eventObject) {
        this.eventObject = eventObject;
        this.timestamp = System.currentTimeMillis();
    }

    public long getTimestamp() {
        return timestamp;
    }
}
