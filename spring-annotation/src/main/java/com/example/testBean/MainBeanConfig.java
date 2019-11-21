package com.example.testBean;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangjie
 * @date 2019/10/28 10:41
 */

/**
 * ComponentScan：包扫描，ex:ComponentScan(basePackages = {"com.example.testBean"})
 *     excludeFilters 排除  ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class}),
 *                          ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {PersonServiceImpl.class})
 *      includeFilters 包含，与useDefaultFilters = false 连用
 * FilterType ：
 *      ANNOTATION ：注解形式 比如@Controller @Service @Repository @Compent
 *      ASSIGNABLE_TYPE： 指定的类型
 *      ASPECTJ ：aspectJ形式的
 *      REGEX ：正则表达式的
 *      CUSTOM： 自定义的
 */
@Configuration
/*@ComponentScan(basePackages = {"com.example.testBean"},excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {PersonServiceImpl.class})
})*/
@ComponentScan(basePackages = {"com.example.testBean"})
/*@ComponentScan(basePackages = {"com.example.testBean"},includeFilters = {
        //@ComponentScan.Filter(type = FilterType.ANNOTATION,value = {Controller.class}),
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,value = {PersonServiceImpl.class}),
        @ComponentScan.Filter(type = FilterType.CUSTOM,value = {MyFilterType.class})
},useDefaultFilters = false)*/
public class MainBeanConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainBeanConfig.class);
        TestBeanConfig testBeanConfig = ctx.getBean(TestBeanConfig.class);
        testBeanConfig.outputResource();

        /*System.out.println("配置加载完毕");
        String[] definitionNames = ctx.getBeanDefinitionNames();
        for (String beanName : definitionNames){
            System.out.println(beanName);
        }*/
       /* Object person1 = ctx.getBean("person2");
        Object person2 = ctx.getBean("person2");
        System.out.println(person1);
        System.out.println(person2);*/
        /*Object person1 = ctx.getBean("person");
        Object person2 = ctx.getBean("personFactoryBean");
        Object person3 = ctx.getBean("personFactoryBean");
        System.out.println(person1);
        System.out.println(person2);
        System.out.println(person3);
        System.out.println(person1 == person2);
        System.out.println(person3 == person2);*/
        ctx.close();
    }

    @Bean
    public PersonFactoryBean personFactoryBean(){
        return new PersonFactoryBean();
    }

    /**
     * Scope(value = "prototype") 为多实例的，而且还是懒汉模式加载（IOC容器启动的时候，并不会创建对象，而是
     * 在第一次使用的时候才会创建）
     *   singleton 单实例的(默认)
     *   prototype 多实例的
     *   request 同一次请求
     *   session 同一个会话级别
     *
     * Lazy懒加载(主要针对单实例的bean 容器启动的时候，不创建对象，在第一次使用的时候才会创建该对象
     *
     * @return
     */
    @Bean("person")
    //@Lazy
    //@Scope(value = "prototype")
    //@Conditional(value = MyCondition.class)
    public Person person(){
        return new Person();
    }


    /**
     * 方式1： @Bean(initMethod = "myInit",destroyMethod = "myDestroy") 管理Bean的生命周期
     * @return
     */
    @Bean(initMethod = "myInit",destroyMethod = "myDestroy")
    public Employee employee(){
        return new Employee();
    }

    /**
     * 方式2：通过 InitializingBean和DisposableBean 的二个接口实现bean的初始化以及销毁
     * @return
     */
    @Bean
    public Employee2 employee2(){
        return new Employee2();
    }

    /**
     * 方式3：通过JSR250规范 提供的注解@PostConstruct 和@ProDestory标注的方法
     * @return
     */
    @Bean
    public Employee3 employee3(){
        return new Employee3();
    }

    /**
     * :通过Spring的BeanPostProcessor的 bean的后置处理器会拦截所有bean创建过程
     * @return
     */
    @Bean
    public EmployeeBeanPostProcessor employeeBeanPostProcessor(){
        return new EmployeeBeanPostProcessor();
    }
}
