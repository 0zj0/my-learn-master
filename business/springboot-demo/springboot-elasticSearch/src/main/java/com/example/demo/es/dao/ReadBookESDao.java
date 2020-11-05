package com.example.demo.es.dao;

import com.example.demo.es.entity.ReadBookESDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 *
 * String : 对应document 行标识
 * @author zhangjie
 * @date 2020/11/4 14:48
 */
public interface ReadBookESDao extends ElasticsearchRepository<ReadBookESDTO,Long> {

}
