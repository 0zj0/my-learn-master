package com.example.demo.observer.one.observer;

import com.example.demo.observer.one.event.MyEvent;

/**
 * 监听器抽象实现类，多了order属性，用于监听器执行顺序
 * @author 张杰
 * @date 2020/2/16 12:06
 */
public class AbstractEventListener implements EventListener {

    /**
     * 监听器顺序
     */
    private int order;

    public AbstractEventListener(int order){
        this.order = order;
    }

    @Override
    public void onTestEvent(MyEvent myEvent) {

    }

    public int getOrder() {
        return order;
    }

}
