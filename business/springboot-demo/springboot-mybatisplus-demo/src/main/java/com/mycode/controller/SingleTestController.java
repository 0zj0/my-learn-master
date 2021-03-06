package com.mycode.controller;

import com.example.entity.ResponseVo;
import com.example.entity.db1.po.SysUserPO;
import com.example.service.db1.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangjie
 * @date 2019/8/13 9:58
 */
@RestController
@RequestMapping("/single")
public class SingleTestController {

    @Autowired
    private ISysUserService sysUserService;

    @RequestMapping("/db1")
    public ResponseVo db1Test(){
        List<SysUserPO> list = sysUserService.listAll();
        list.forEach(sysUserPO -> {
            System.out.println(sysUserPO.toString());
        });
        return ResponseVo.success();
    }

}
