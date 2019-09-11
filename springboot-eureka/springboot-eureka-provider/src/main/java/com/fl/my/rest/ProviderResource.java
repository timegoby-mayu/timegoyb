package com.fl.my.rest;


import com.fl.my.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/provider")
public class ProviderResource {

    @Autowired
    private ClientService clientService;

    /*
    调用client服务测试
     * */
    @RequestMapping("/client")
    public String clinetTest() {
        return clientService.clientMethod ();
    }

    /*通过client调用自己*/
    @RequestMapping("/client/mysefl")
    public String myseflTest() {
        return clientService.mysefl ()+"通过client项目的8081端口访问的自己！";
    }

    /*
    提供给clinet的服务
    * */
    @RequestMapping("/test")
    public String test() {
        return "我是springboot-enreka-provider项目测试方法！端口为9090";
    }
}
