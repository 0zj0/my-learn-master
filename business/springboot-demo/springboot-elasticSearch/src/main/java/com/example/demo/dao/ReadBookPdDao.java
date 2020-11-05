package com.example.demo.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.ReadBookPd;
import com.example.demo.es.entity.ReadBookESDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author zhangjie
 * @date 2020/10/14 18:40
 */
public interface ReadBookPdDao extends BaseMapper<ReadBookPd> {

    IPage<ReadBookPd> selectByPage(IPage<ReadBookPd> page, @Param("cacheId") int id);


    List<ReadBookESDTO> selectList2(int cacheId);
}
