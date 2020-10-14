package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ReadBookPd;
import com.example.demo.service.IReadBookPdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/query/page")
    public Object queryPage(){
        IPage<ReadBookPd> page = new Page<>();
        page = readBookPdService.selectByPage(page);
        return page;
    }

}


