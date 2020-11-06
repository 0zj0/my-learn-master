package com.example.demo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.entity.ReadBookPd;
import com.example.demo.es.entity.ReadBookESDTO;
import com.example.demo.es.service.ReadBookESUtil;
import com.example.demo.result.Result;
import com.example.demo.service.IReadBookPdService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangjie
 * @date 2020/10/14 18:53
 */
@RestController
@RequestMapping("/api")
@Api(tags = "测试接口")
public class ReadBookController {

    @Autowired
    private IReadBookPdService readBookPdService;

    @Autowired
    private ReadBookESUtil readBookESUtil;

    @GetMapping("/query/page")
    @ApiOperation(value = "分页查询测试",httpMethod = "GET")
    public Object queryPage(){
        IPage<ReadBookPd> page = new Page<>();
        page = readBookPdService.selectByPage(page);
        return page;
    }

    @GetMapping("/query/list2")
    @ApiOperation(value = "list查询测试",httpMethod = "GET")
    public Object queryList2(){
        return readBookPdService.selectList2();
    }

    @PostMapping("/es/insert/one")
    @ApiOperation(value = "es循环添加数据",httpMethod = "POST")
    public Result esInsertTest(){
        readBookESUtil.addData();
        return new Result(true);
    }

    @PostMapping("/es/insert/two")
    @ApiOperation(value = "es循环添加数据",httpMethod = "POST")
    public Result esInsertTwo(){
        readBookESUtil.addData();
        return new Result(true);
    }

    @PostMapping("/es/insert/batch")
    @ApiOperation(value = "es批量添加数据",httpMethod = "POST")
    public Result esInsertbatch(){
        readBookESUtil.addBatchData();
        return new Result(true);
    }

    @PostMapping("/es/insert/three")
    @ApiOperation(value = "es添加数据",httpMethod = "POST")
    public Result esInsertTree(){
        readBookESUtil.addBatchData2();
        return new Result(true);
    }

    @DeleteMapping("/es/delete/{id}")
    @ApiOperation(value = "es删除数据",httpMethod = "DELETE")
    public Result esDelete(@PathVariable Long id){
        readBookESUtil.deleteData(id);
        return new Result(true);
    }

    @GetMapping("/es/get/{id}")
    @ApiOperation(value = "es主键查询",httpMethod = "GET")
    public Result esGetById(@PathVariable Long id){
        ReadBookESDTO bookESDTO = readBookESUtil.getEsDataById(id);
        return new Result(true).setInfo(bookESDTO);
    }

    @PutMapping("/es/update/{id}")
    @ApiOperation(value = "es更新&添加信息",httpMethod = "PUT")
    public Result esUpdateById(@PathVariable Long id){
        readBookESUtil.updateById(id);
        return new Result(true);
    }

    /**
     * 关键字搜索 + 分页 + 排序
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/es/query/simple")
    @ApiOperation(value = "es简单搜索",httpMethod = "GET")
    public Result esSimpleQuery(@RequestParam(required = false) String keyword,
                                @RequestParam(required = false, defaultValue = "0") Integer pageNum,
                                @RequestParam(required = false, defaultValue = "10") Integer pageSize){
        org.springframework.data.domain.Page<ReadBookESDTO> readBookESDTOS = readBookESUtil.esSimpleQuery(keyword, pageNum, pageSize);
        return new Result(true).setInfo(readBookESDTOS);
    }

    @GetMapping("/es/query/complex")
    @ApiImplicitParam(name = "sort", value = "排序字段:0->按相关度；1->按新品；2->价格从低到高；3->价格从高到低",
            defaultValue = "0", allowableValues = "0,1,2,3", paramType = "query", dataType = "integer")
    @ApiOperation(value = "es复杂搜索",httpMethod = "GET")
    public Result esComplexQuery(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String category,
                                 @RequestParam(required = false, defaultValue = "0") int minPrice,
                                 @RequestParam(required = false, defaultValue = "0") int maxPrice,
                                 @RequestParam(required = false, defaultValue = "0") int pageNum,
                                 @RequestParam(required = false, defaultValue = "10") int pageSize,
                                 @RequestParam(required = false, defaultValue = "0") int sort){
        org.springframework.data.domain.Page<ReadBookESDTO> readBookESDTOS = readBookESUtil.esComplexQuery(keyword, category, minPrice, maxPrice, pageNum, pageSize, sort);
        return new Result(true).setInfo(readBookESDTOS);
    }

}


