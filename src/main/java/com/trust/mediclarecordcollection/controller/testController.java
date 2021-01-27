package com.trust.mediclarecordcollection.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @auther wangnanbei
 * @date 2020/7/3 17:15
 */

@RestController
public class testController {



    @RequestMapping("/info")
    public void test(@RequestBody String json){
        System.out.println(json);
    }

}
