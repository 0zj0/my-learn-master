jar包下载地址：https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/


通过rabbitmq 进行传输链路数据
启动命令：java -jar zipkin-server-2.12.2-exec.jar --zipkin.collector.rabbitmq.addresses=localhost
（默认guest账户）

//访问localhost:9411 可以进入ui监控

关联项目配置：
添加依赖：
<dependency>
    <groupId>org.springframework.amqp</groupId>
    <artifactId>spring-rabbit</artifactId>
</dependency>

添加配置：
spring.sleuth.sampler.probability=1
spring.sleuth.web.enabled=true
spring.zipkin.sender.type=rabbit
#spring.zipkin.base-url=http://localhost:8807/

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest


