package com.example.testEvent;

import org.springframework.context.ApplicationEvent;

/**
 * @author zhangjie
 * @date 2019/11/15 16:54
 */
public class DemoEvent extends ApplicationEvent {

    private String msg;


    /**
     * Create a new ApplicationEvent.
     *
     * @param source the object on which the event initially occurred (never {@code null})
     */
    public DemoEvent(Object source,String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
