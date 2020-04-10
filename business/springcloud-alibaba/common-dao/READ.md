1. 添加以下依赖用于读取自定义的bootstrap.yml配置文件
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-context</artifactId>
</dependency>

2.在spring boot入口方法上加上注解
//禁用spring自动配置数据库
@EnableAutoConfiguration(exclude = DataSourceAutoConfiguration.class)