package com.fl.my.rest;

import com.fl.my.service.FeignTwoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/one/consumer")
public class ConsumerResoure
{

    @Autowired
    public FeignTwoService eurekaProvider;

    @RequestMapping("/test")
    public String providerServerTest() {
        return eurekaProvider.providerTest ();
    }

}
