package com.example.demo.es.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.ReadBookPdDao;
import com.example.demo.entity.ReadBookPd;
import com.example.demo.es.dao.ReadBookESDao;
import com.example.demo.es.entity.ReadBookESDTO;
import com.example.redis.RedisHelper;
import com.example.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjie
 * @date 2020/11/4 14:54
 */
@Service
@Slf4j
public class ReadBookESUtil {

    @Autowired
    private ReadBookESDao readBookESDao;

    @Resource
    private ReadBookPdDao readBookPdDao;

    @Resource
    private RedisHelper redisHelper;

    /**id缓存**/
    private static final String CACHE_DATA_ID = "cache:data:id";

    public void addData(){
        //1. mysql 获取数据
        IPage<ReadBookPd> page = new Page<>();
        int cacheId = getCacheId();
        page = readBookPdDao.selectByPage(page, cacheId);
        List<ReadBookPd> list = page.getRecords();
        if(CollectionUtils.isEmpty(list)){
            log.info("查询数据为空");
            return;
        }
        int cacheId2 = list.get(list.size() - 1).getId();
        //2. es 添加数据（循环）
        List<ReadBookESDTO> bookESDTOS = new ArrayList<>();
        for (ReadBookPd readBookPd : list) {
            bookESDTOS.add(transforToESDTO(readBookPd));
        }

        for (ReadBookESDTO bookESDTO : bookESDTOS) {
            readBookESDao.save(bookESDTO);
        }

        redisHelper.set(CACHE_DATA_ID,cacheId2);
    }

    public void addBatchData(){
        //1. mysql 获取数据
        IPage<ReadBookPd> page = new Page<>();
        int cacheId = getCacheId();
        page = readBookPdDao.selectByPage(page, cacheId);
        List<ReadBookPd> list = page.getRecords();
        if(CollectionUtils.isEmpty(list)){
            log.info("查询数据为空");
            return;
        }
        int cacheId2 = list.get(list.size() - 1).getId();
        //2. es 添加数据（循环）
        List<ReadBookESDTO> bookESDTOS = new ArrayList<>();
        for (ReadBookPd readBookPd : list) {
            bookESDTOS.add(transforToESDTO(readBookPd));
        }
        readBookESDao.saveAll(bookESDTOS);
        redisHelper.set(CACHE_DATA_ID,cacheId2);
    }

    public void addBatchData2(){
        //1. mysql 获取数据
        int cacheId = getCacheId();
        List<ReadBookESDTO> list = readBookPdDao.selectList2(cacheId);
        if(CollectionUtils.isEmpty(list)){
            log.info("查询数据为空");
            return;
        }
        int cacheId2 = list.get(list.size() - 1).getId().intValue();
        readBookESDao.saveAll(list);
        redisHelper.set(CACHE_DATA_ID,cacheId2);
    }

    private int getCacheId(){
        if(!redisHelper.exists(CACHE_DATA_ID)){
            return 0;
        }
        return redisHelper.get(CACHE_DATA_ID,Integer.class);
    }

    private ReadBookESDTO transforToESDTO(ReadBookPd bookPd){
        ReadBookESDTO esdto = new ReadBookESDTO();
        esdto.setId(Long.parseLong(bookPd.getId()+""));
        esdto.setName(bookPd.getName());
        esdto.setEnName(bookPd.getEnName());
        esdto.setAuthor(bookPd.getAuthor());
        esdto.setImgurl(bookPd.getImgurl());
        esdto.setDiscription(bookPd.getDiscription());
        esdto.setCreator(bookPd.getCreator());
        /*esdto.setCreateTime(DateTimeUtils.convertLocalDateTimeToDate(bookPd.getCreateTime()));
        esdto.setUpdateTime(DateTimeUtils.convertLocalDateTimeToDate(bookPd.getUpdateTime()));*/
        esdto.setCreateTime(DateTimeUtils.getTimestampOfDateTime(bookPd.getCreateTime()));
        esdto.setUpdateTime(DateTimeUtils.getTimestampOfDateTime(bookPd.getUpdateTime()));
        esdto.setStatus(bookPd.getStatus());
        esdto.setCommentNum(bookPd.getCommentNum());
        esdto.setPrice(bookPd.getPrice());
        esdto.setCategory(bookPd.getCategory());
        return esdto;
    }
}
