package com.example.demo.config;

import com.example.demo.bean.TestBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjie
 * @date 2019/9/6 18:44
 */
@Configuration
public class TestConfig {

    @Bean
    public TestBean getTestBean(){
        TestBean testBean = new TestBean();
        testBean.setAge(1);
        testBean.setName("hehe");
        return testBean;
    }

}
