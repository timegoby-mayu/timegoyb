package com.fl.my.rest;

import com.fl.my.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientResoure {

    @Autowired
    public ProviderService eurekaProvider;

    @RequestMapping("/provider")
    public String providerServerTest() {
        return eurekaProvider.providerTest ();
    }

    /* *
      为springboot-eureka-provider提供服务
    */
    @RequestMapping("/test")
    public String test1() {
        return "我是springboot-eureka-client项目测试方法！端口为8081";
    }
}
