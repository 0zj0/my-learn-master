package com.example.demo.test;

import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import java.io.IOException;

/**
 * @author zhangjie
 * @date 2020/11/4 11:35
 */
public class RestHighLevelClientTest {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("139.9.222.86",19200,"http")));

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices("megacorp");
        searchRequest.types("employee");
        searchRequest.source(searchSourceBuilder);
        SearchResponse response = client.search(searchRequest);
        System.out.println("****************************************");
        System.out.println(response);
        System.out.println(response.getHits());
        System.out.println("****************************************");
        client.close();
    }

}
