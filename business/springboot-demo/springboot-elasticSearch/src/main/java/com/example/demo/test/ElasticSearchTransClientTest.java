package com.example.demo.test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhangjie
 * @date 2020/11/4 14:19
 */
public class ElasticSearchTransClientTest {


    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name", "my-es").build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("139.9.222.86"),Integer.parseInt("19300")));

        SearchResponse response = client.prepareSearch("megacorp").setTypes("employee")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(QueryBuilders.queryStringQuery("last_name:Smith"))
                .setFrom(0).setSize(10).setExplain(false).get();
        System.out.println(response);
        client.close();
    }
}
