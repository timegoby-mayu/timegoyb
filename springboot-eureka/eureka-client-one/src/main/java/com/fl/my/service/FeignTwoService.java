package com.fl.my.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
@FeignClient(name = "eureka-client-two")
public interface FeignTwoService
{

    /*调用provider测试*/
    @GetMapping("/provider/test")
    public String providerTest();

}
