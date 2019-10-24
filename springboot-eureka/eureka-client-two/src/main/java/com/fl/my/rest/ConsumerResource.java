package com.fl.my.rest;


import com.fl.my.service.FeignOneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/two/consumer")
public class ConsumerResource
{

    @Autowired
    private FeignOneService feignOneService;

    /*
    调用client服务测试
     * */
    @RequestMapping("/test")
    public String clinetTest() {
        return feignOneService.clientMethod ();
    }

    /*通过client调用自己*/
    @RequestMapping("/myseflTest")
    public String myseflTest() {
        //return feignOneService.mysefl ()+"通过client项目的8081端口访问的自己！";//错误方式调用
        return  null;
    }

}
