package com.fly.my;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.velocity.VelocityAutoConfiguration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
//@ImportResource({"classpath:spring-velocity.xml"})
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run (SpringSecurityApplication.class, args);
    }

    @RequestMapping("/test")
    public String getMessage() {
        return "请求服务器成功，时间：" + System.currentTimeMillis ();
    }

}
