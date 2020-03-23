package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author 张杰
 * @date 2020/3/18 14:55
 */
@SpringBootApplication
public class Application {

    /**
     * 初始JVM 配置：
     * -Xms1536M -Xmx1536M -Xmn512M -Xss256K -XX:SurvivorRatio=6  -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M
     * -XX:+UseParNewGC  -XX:+UseConcMarkSweepGC  -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly
     * 优化JVM 配置：
     * -Xms1536M -Xmx1536M -Xmn1024M -Xss256K -XX:SurvivorRatio=6  -XX:MetaspaceSize=256M -XX:MaxMetaspaceSize=256M
     * -XX:+UseParNewGC  -XX:+UseConcMarkSweepGC  -XX:CMSInitiatingOccupancyFraction=92 -XX:+UseCMSInitiatingOccupancyOnly
     * 代码优化：
     * com.jvm.IndexController#queryUsers() 中，获取5000个对象，每个对象100KB,共500M,占用内存过大，容易发生full GC；优化方案 减少获取对象个数
     *
     * 调优插件：
     * jstat -gc pid 1000（ms） 5(次) 打印GC 收集情况
     * jmap -histo pid 查看对象实例情况
     * jstack 找出占用cpu最高的堆栈信息
     * 也可以使用jvisualVM 工具
     * jvisualVM -> profiler -> 内存 ： 相当于jmap
     * jvisualVM -> profiler -> CPU ： 相当于jstack
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
