package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhangjie
 * @date 2020/10/14 16:47
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("read_book_pd")
public class ReadBookPd extends Model<ReadBookPd> {

    private static final long serialVersionUID=1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 书名
     */
    @TableField("name")
    private String name;

    /**
     * 书名-英文
     */
    @TableField("en_name")
    private String enName;

    /**
     * 作者
     */
    @TableField("author")
    private String author;

    /**
     * 图片
     */
    @TableField("imgurl")
    private String imgurl;

    /**
     * 描述
     */
    @TableField("discription")
    private String discription;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 创建时间
     */
    @TableField("create_time")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private LocalDateTime updateTime;

    /**
     * 1正常，-1删除，0下架
     */
    @TableField("status")
    private int status;

    /**
     * 评论数
     */
    @TableField("comment_num")
    private int commentNum;

    /**
     * 价格，分
     */
    @TableField("price")
    private int price;

    /**
     * 类别
     */
    @TableField("category")
    private String category;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
