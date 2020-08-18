package com.example.demo.configurer;


import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.net.InetAddress;

@Service
@Slf4j
public class ElasticSearchManger implements InitializingBean {

    public TransportClient client;
    public Settings settings;

    public void init(){
        settings = Settings.builder().put("cluster.name", "my-es")
                .build();
        try {
            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(new TransportAddress(InetAddress.getByName("139.9.222.36"), Integer.parseInt("19300")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("对es进行初始化");
        init();
        log.info("es初始化完成");
    }

    public static void main(String[] args) {
        ElasticSearchManger elasticSearchManger = new ElasticSearchManger();
        elasticSearchManger.init();
        SearchResponse response = elasticSearchManger.client.prepareSearch("bbg_goods").setTypes("item_loc")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH).setQuery(QueryBuilders.queryStringQuery("item_desc:格力"))
                .setFrom(0).setSize(10).setExplain(false).get();
        System.out.println(response);
    }
}
