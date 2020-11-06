package com.example.demo.es.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.dao.ReadBookPdDao;
import com.example.demo.entity.ReadBookPd;
import com.example.demo.es.dao.ReadBookESDao;
import com.example.demo.es.entity.ReadBookESDTO;
import com.example.redis.RedisHelper;
import com.example.utils.DateTimeUtils;
import com.example.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    /**
     * 获取数据，循环转换格式，循环添加到es
     */
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

    /**
     * 获取数据，循环转换格式，批量添加到es
     */
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

    /**
     * 获取数据（格式已转化），批量添加到es
     */
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

    /**
     * es 删除数据
     * @param id
     */
    public void deleteData(Long id) {
        readBookESDao.deleteById(id);
    }

    /**
     * es 主键查询
     * @param id
     */
    public ReadBookESDTO getEsDataById(Long id) {
        Optional<ReadBookESDTO> byId = readBookESDao.findById(id);
        return byId.orElseGet(null);
    }

    /**
     * 通过id查询数据，并更新es数据
     * @param id
     */
    public void updateById(Long id) {
        ReadBookPd readBookPd = readBookPdDao.selectById(id.intValue());
        if(readBookPd == null){
            log.info("数据库数据不存在");
            return;
        }
        readBookPd.setCreator("zj:"+System.currentTimeMillis());
        readBookESDao.save(transforToESDTO(readBookPd));
    }

    /**
     * es 通过关键字 分页查询
     * @param keyword
     * @param pageNum
     * @param pageSize
     */
    public org.springframework.data.domain.Page<ReadBookESDTO> esSimpleQuery(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return readBookESDao.findByNameOrDiscriptionOrderByIdAsc(keyword,keyword,pageable);
    }

    /**
     * es 复杂查询
     * @param keyword
     * @param category
     * @param minPrice
     * @param maxPrice
     * @param pageNum
     * @param pageSize
     * @param sort
     */
    public org.springframework.data.domain.Page<ReadBookESDTO> esComplexQuery(String keyword, String category, int minPrice, int maxPrice, int pageNum, int pageSize, int sort) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        //设置分页
        queryBuilder.withPageable(pageable);
        //过滤
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolean filter = false;
        if(!StringUtil.isEmpty(category)){
            boolQueryBuilder.must(QueryBuilders.termQuery("category",category));
            filter = true;
        }
        if(minPrice > 0){
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").gte(minPrice));
            filter = true;
        }
        if(maxPrice > 0){
            boolQueryBuilder.must(QueryBuilders.rangeQuery("price").lte(maxPrice));
            filter = true;
        }
        if(filter){
            queryBuilder.withFilter(boolQueryBuilder);
        }
        //搜索
        if(StringUtil.isEmpty(keyword)){
            //关键字不存在
            queryBuilder.withQuery(QueryBuilders.matchAllQuery());
        }else{
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            //name 匹配权重:10
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name",keyword),
                    ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("discription",keyword),
                    ScoreFunctionBuilders.weightFactorFunction(5)));
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                    .setMinScore(2);
            queryBuilder.withQuery(functionScoreQueryBuilder);
        }
        //排序 0->按相关度；1->按新品；2->价格从低到高；3->价格从高到低
        if(sort == 1){
            //按新品 : 创建时间倒叙
            queryBuilder.withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC));
        }else if(sort == 2){
            //按价格从低到高
            queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        }else if(sort == 3){
            //价格从高到低
            queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        }else{
            //按相关度
            queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }
        //相同排序值，按相关度倒叙
        if(sort != 0){
            queryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        }

        //查询：
        NativeSearchQuery searchQuery = queryBuilder.build();
        log.info("DSL:{}",searchQuery.getQuery().toString());
        return readBookESDao.search(searchQuery);
    }


    private int getCacheId(){
        if(!redisHelper.exists(CACHE_DATA_ID)){
            return 0;
        }
        return redisHelper.get(CACHE_DATA_ID,Integer.class);
    }

    /**
     * 对象转换
     * @param bookPd
     * @return
     */
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
