package com.example.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.entity.ReadBookPd;
import com.example.demo.es.entity.ReadBookESDTO;

import java.util.List;

/**
 * @author zhangjie
 * @date 2020/10/14 18:41
 */
public interface IReadBookPdService {


    IPage<ReadBookPd> selectByPage(IPage<ReadBookPd> page);

    List<ReadBookESDTO> selectList2();
}
