package com.example.testBean;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

/**
 * @author zhangjie
 * @date 2019/11/21 17:59
 */
@Component
//@PropertySource("classpath:test.properties")
public class TestBeanConfig {

    /**
     * 注入普通字符串
     */
    @Value("I LOVE YOU！")
    private String normal;

    /**
     * 注入操作系统属性
     */
    @Value("#{systemProperties['os.name']}")
    private String osName;

    /**
     * 注入表达式结果
     */
    @Value("#{ T(java.lang.Math).random() * 100.0}")
    private double randomValue;

    /**
     * 注入其他bean 属性
     */
    /*@Value("#{personService.servcieValue}")
    private String fromAnother;*/

    /**
     * 注入文件资源
     */
    /*@Value("classpath:test.properties")
    private Resource testFile;*/

    /**
     * 注入网址资源
     */
    @Value("http://www.baidu.com")
    private Resource testUrl;

    /**
     * 注入配置文件
     */
    /*@Value("${book.name}")
    private String bookName;*/

    @Autowired
    private Environment environment;

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    public void  outputResource(){
        try {
            System.out.println(normal);
            System.out.println(osName);
            System.out.println(randomValue);
            //System.out.println(fromAnother);
            //System.out.println(IOUtils.toString(testFile.getInputStream()));
            System.out.println(IOUtils.toString(testUrl.getInputStream()));
            //System.out.println(bookName);
            //System.out.println(environment.getProperty("book.author"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




}
