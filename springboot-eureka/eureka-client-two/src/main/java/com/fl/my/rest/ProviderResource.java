package com.fl.my.rest;

import com.fl.my.service.FeignOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2019/10/24$ 17:31$
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/provider")
public class ProviderResource
{
    /*
    提供给clinet的服务
    * */
    @RequestMapping("/test")
    public String test() {
        return "我是eureka-client-two项目测试方法！端口为9090";
    }
}
