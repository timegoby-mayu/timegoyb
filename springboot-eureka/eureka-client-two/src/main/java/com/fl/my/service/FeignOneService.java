package com.fl.my.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "eureka-client-two")
public interface FeignOneService
{

    @RequestMapping("/provider/test")
    public String clientMethod();

}
