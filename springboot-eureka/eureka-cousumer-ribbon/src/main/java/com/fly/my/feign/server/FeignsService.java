package com.fly.my.feign.server;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = "springboot-eureka-provider")
public interface FeignsService
{

    @RequestMapping("/ribbon/test")
    public String ribbon();

}
