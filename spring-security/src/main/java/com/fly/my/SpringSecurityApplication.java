package com.fly.my;

import com.sun.org.glassfish.gmbal.ParameterNames;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@SpringBootApplication
@RestController
public class SpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run (SpringSecurityApplication.class, args);
    }

    @GetMapping("/test")
    public String getMessage() {
        return "请求服务器成功，时间：" + System.currentTimeMillis ();
    }

}
