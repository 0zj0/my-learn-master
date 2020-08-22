package com.example.beanDefinition;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangjie
 * @date 2020/8/20 19:51
 */
public class SpringTest4 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        //注册配置类
        context.register(ConfigTest.class);
        //加载或者刷新当前的配置信息
        context.refresh();

        //获取InterService 对应的beanDefinition,默认名称为 interService
        BeanDefinition definition = context.getBeanDefinition("interService");
        System.out.println(definition.getSource());
        //file [F:\MyGit\my-learn-master\spring-annotation\target\classes\com\example\beanDefinition\InterService.class]
    }

}
