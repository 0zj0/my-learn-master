package com.example.testImport;

import com.example.testBean.Person;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 *通过 ImportBeanDefinitionRegistrar 的实现类 导入组件（可以指定bean的名称）
 * @author zhangjie
 * @date 2019/10/28 15:32
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //创建一个bean 定义对象
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Person.class);

        //把bean 定义对象导入容器中,并命名
        registry.registerBeanDefinition("myperson",rootBeanDefinition);
    }
}
