package com.example.demo.controller;

import com.example.demo.service.RibbonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 张杰
 * @date 2020/5/6 21:40
 */
@RestController
public class RibbonController {

    @Autowired
    private RibbonService ribbonService;

    @GetMapping("/hi")
    public String hi(@RequestParam String name){
        return ribbonService.hi(name);
    }

}
