package com.fly.my.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2020/4/1$ 19:23$
 * @Version: 1.0
 **/
@RestController
@RequestMapping("api")
public class login
{
    @RequestMapping("/test")
    public String test(){
        return "请求服务器成功，时间：" + System.currentTimeMillis ();
    }
}
