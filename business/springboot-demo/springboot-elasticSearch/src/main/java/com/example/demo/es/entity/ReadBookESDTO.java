package com.example.demo.es.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;

/**
 * @author zhangjie
 * @date 2020/11/4 14:43
 */
@Data
@Document(indexName = "my_book_test",type = "read_book",shards = 1,replicas = 0)
public class ReadBookESDTO implements Serializable {

    private static final long serialVersionUID = -5527725164965908017L;

    @Id
    private Long id;

    /**
     * 书名 (可以被分词搜索：存储 最大分词， 查询 最小分词)
     */
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String name;

    /**
     * 书名-英文 （英文分词）
     */
    @Field(type = FieldType.Text,analyzer = "english")
    private String enName;

    /**
     * 作者 (不分词匹配)
     */
    @Field(type = FieldType.Keyword)
    private String author;

    /**
     * 图片
     */
    private String imgurl;

    /**
     * 描述 (最大分词)
     */
    @Field(analyzer = "ik_max_word",type = FieldType.Text)
    private String discription;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @Field(type = FieldType.Long)
    private long createTime;

    /**
     * 修改时间
     */
    @Field(type = FieldType.Long)
    private long updateTime;

    /**
     * 1正常，-1删除，0下架
     */
    private int status;

    /**
     * 评论数
     */
    private int commentNum;

    /**
     * 价格，分
     */
    private int price;

    /**
     * 类别
     */
    @Field(type = FieldType.Keyword)
    private String category;

}
