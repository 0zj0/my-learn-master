package com.example.testEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangjie
 * @date 2019/11/15 17:02
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {


    @Override
    public void onApplicationEvent(DemoEvent event) {
        String msg = event.getMsg();
        System.out.println("DemoListener 监听信息:"+msg);
    }
}
