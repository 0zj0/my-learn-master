package com.example.demo.filter;

import org.apache.rocketmq.common.filter.FilterContext;
import org.apache.rocketmq.common.filter.MessageFilter;
import org.apache.rocketmq.common.message.MessageExt;

public class MessageFilterImpl implements MessageFilter {

    @Override
    public boolean match(MessageExt msg, FilterContext context) {
        System.out.println(msg.toString());
        String property = msg.getProperty("a");
        if (property != null) {
            int id = Integer.parseInt(property);
            if (id % 2 == 0) {
                return true;
            }
        }

        return false;
    }
}
