package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.ReadBookPd;
import org.apache.ibatis.annotations.Select;

/**
 * @author zhangjie
 * @date 2020/10/14 18:40
 */
public interface ReadBookPdDao extends BaseMapper<ReadBookPd> {


    @Select("select * from read_book_pd ")
    IPage<ReadBookPd> selectByPage(IPage<ReadBookPd> page);
}
