package com.fl.my.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "springboot-eureka-client")
public interface ClientService {

    @RequestMapping("/client/test")
    public String clientMethod();

    /**
     * 通过client再调用自己测试（只是测试）
     */
    @RequestMapping("/client/provider")
    public String mysefl();
}
