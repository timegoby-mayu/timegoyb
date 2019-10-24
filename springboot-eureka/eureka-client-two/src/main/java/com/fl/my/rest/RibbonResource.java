package com.fl.my.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2019/10/24$ 15:14$
 * @Version: 1.0
 **/
@RestController
@RequestMapping("/ribbon")
public class RibbonResource
{

    @RequestMapping("/test")
    public String providerServerTest(HttpServletRequest request)
    {
        return request.getLocalName () + "端口：" + request.getServerPort ();
    }
}
