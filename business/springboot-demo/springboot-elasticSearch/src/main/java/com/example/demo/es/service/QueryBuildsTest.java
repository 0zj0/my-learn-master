package com.example.demo.es.service;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

/**
 * @author zhangjie
 * @date 2020/11/6 14:47
 */
public class QueryBuildsTest {

    /**
     * match query 单个匹配
     * 会解析查询字符串，判断是否进行分词，然后查询
     * @return
     */
    public static QueryBuilder matchQuery(){
        return QueryBuilders.matchQuery("field","value");
    }

    /**
     * term query 单个匹配
     * 匹配查询(匹配最小分词)
     * @return
     */
    public static QueryBuilder termQuery(){
        return QueryBuilders.termQuery("field","value");
    }

    /**
     * terms query
     * 一个查询相匹配的多个value
     */
    protected static QueryBuilder termsQuery() {
        return QueryBuilders.termsQuery("field",
                "value1", "value2");
    }

    /**
     * multiMatch query 多字段匹配查询
     * @return
     */
    public static QueryBuilder multiMatchQuery(){
        return QueryBuilders.multiMatchQuery("value","field1","filed2");
    }

    /**
     * boolean query and 条件组合查询
     * @return
     */
    public static QueryBuilder booleanQuery(){
        return QueryBuilders.boolQuery()
                .must(QueryBuilders.termQuery("field1", "value1"))
                .must(QueryBuilders.termQuery("field2", "value2"))
                .mustNot(QueryBuilders.termQuery("field3", "value3"))
                .should(QueryBuilders.termQuery("field4", "value4"));
    }

    /**
     * fuzzy query  使用模糊查询匹配文档查询。
     * @return
     */
    public static QueryBuilder fuzzyQuery(){
        return QueryBuilders.fuzzyQuery("filed","value");
    }

    /**
     * prefix query 包含与查询相匹配的文档指定的前缀。
     * @return
     */
    public static QueryBuilder prefixQuery() {
        return QueryBuilders.prefixQuery("filed", "value");
    }

    /**
     * range query
     * 查询相匹配的文档在一个范围。
     */
    public static QueryBuilder rangeQuery() {
        return QueryBuilders
                .rangeQuery("filed")
                .from("minValue")
                .to("maxValue")
                .includeLower(true)     //包括下界
                .includeUpper(false); //包括上界
    }

}
