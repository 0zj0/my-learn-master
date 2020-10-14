package com.example.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.demo.dao.ReadBookPdDao;
import com.example.demo.entity.ReadBookPd;
import com.example.demo.service.IReadBookPdService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhangjie
 * @date 2020/10/14 18:41
 */
@Service
@Slf4j
public class ReadBookPdServiceImpl implements IReadBookPdService {

    @Resource
    private ReadBookPdDao readBookPdDao;

    @Override
    public IPage<ReadBookPd> selectByPage(IPage<ReadBookPd> page) {
        return readBookPdDao.selectByPage(page);
    }
}
