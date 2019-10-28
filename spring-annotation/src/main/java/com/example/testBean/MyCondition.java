package com.example.testBean;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author zhangjie
 * @date 2019/10/28 15:00
 */
public class MyCondition implements Condition {

    /**
     * 确定条件是否匹配
     * @param conditionContext 条件上下文
     * @param annotatedTypeMetadata 元数据
     * @return
     */
    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String[] definitionNames = conditionContext.getBeanFactory().getBeanDefinitionNames();
        for(String beanName : definitionNames){
            System.out.println("MyCondition--matches--definitionNames:"+beanName);
        }
        return conditionContext.getBeanFactory().containsBean("personController");
    }
}
