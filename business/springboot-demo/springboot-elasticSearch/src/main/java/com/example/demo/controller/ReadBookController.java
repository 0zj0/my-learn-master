package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ReadBookPd;
import com.example.demo.es.service.ReadBookESUtil;
import com.example.demo.result.Result;
import com.example.demo.service.IReadBookPdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangjie
 * @date 2020/10/14 18:53
 */
@RestController
@RequestMapping("/api")
public class ReadBookController {

    @Autowired
    private IReadBookPdService readBookPdService;

    @Autowired
    private ReadBookESUtil readBookESUtil;

    @GetMapping("/query/page")
    public Object queryPage(){
        IPage<ReadBookPd> page = new Page<>();
        page = readBookPdService.selectByPage(page);
        return page;
    }

    @GetMapping("/query/list2")
    public Object queryList2(){
        return readBookPdService.selectList2();
    }

    @PostMapping("/es/insert/one")
    public Result esInsertTest(){
        readBookESUtil.addData();
        return new Result(true);
    }

    @PostMapping("/es/insert/two")
    public Result esInsertTwo(){
        readBookESUtil.addData();
        return new Result(true);
    }

    @PostMapping("/es/insert/batch")
    public Result esInsertbatch(){
        readBookESUtil.addBatchData();
        return new Result(true);
    }

    @PostMapping("/es/insert/three")
    public Result esInsertTree(){
        readBookESUtil.addBatchData2();
        return new Result(true);
    }
}


