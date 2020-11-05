package com.example.demo.es.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * @author zhangjie
 * @date 2020/11/4 14:52
 */
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.example.demo.es.dao")
public class EsBaseConfig {
}
