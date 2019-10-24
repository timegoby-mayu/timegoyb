package com.fly.my.controller;

import com.fly.my.feign.server.FeignsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2019/10/24$ 14:59$
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/api")
public class RibbonController
{

    @Autowired
    FeignsService feignsService;

    /**
     * @param
     * @return
     * @throws
     * @author timegoby
     * @Description Ribbon客户端负载均衡测试（将其它两个model注册到Eureka上的应用名相同）
     * @date 2019/10/24
     */
    @RequestMapping("/ribbon/test")
    public String ribbonTest()
    {
        return feignsService.ribbon ();
    }
}
