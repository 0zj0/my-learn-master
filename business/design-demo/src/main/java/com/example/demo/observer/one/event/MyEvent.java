package com.example.demo.observer.one.event;

import java.io.Serializable;
import java.util.EventObject;

/**
 * 事件的接口类，可以设置或获取数据EventObject
 * 事件接口
 * @author 张杰
 * @date 2020/2/16 11:51
 */
public interface MyEvent extends Serializable {

    /**
     * 获取事件持有的数据对象
     * @return java.util.EventObject
     * @author 张杰
     * @date 2020/2/16 12:00
     */
    EventObject getEventObject();

    /**
     * 设置事件持有的数据对象
     * @param eventObject 数据对象
     * @return void
     * @author 张杰
     * @date 2020/2/16 11:59
     */
    void setEventObject(EventObject eventObject);
}
