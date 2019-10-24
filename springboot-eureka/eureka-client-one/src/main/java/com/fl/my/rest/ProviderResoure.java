package com.fl.my.rest;

import com.fl.my.service.FeignTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class ProviderResoure
{

    @Autowired
    public FeignTwoService eurekaProvider;

    /* *
      为springboot-eureka-provider提供服务
    */
    @RequestMapping("/test")
    public String test1()
    {
        return "我是eureka-client-one项目测试方法！端口为8081";
    }

}
