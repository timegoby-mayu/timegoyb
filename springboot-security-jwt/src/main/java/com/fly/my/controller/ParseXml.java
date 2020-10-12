package com.fly.my.controller;

import com.fly.my.Util.xml.XmlBuilder;
import com.fly.my.Util.xml.XmlRootBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2020/4/1$ 19:23$
 * @Version: 1.0
 **/
@RestController
@RequestMapping("xml")
public class ParseXml {

    @RequestMapping("/parse")
    public String download() throws Exception {
        //读取Resource目录下的XML文件
        Resource resource = new ClassPathResource("XMLData.XML");
        //利用输入流获取XML文件内容
        BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = br.readLine()) != null) {
            buffer.append(line);
        }
        br.close();
        //XML转为JAVA对象
        XmlRootBean xmlRootBean = (XmlRootBean) XmlBuilder.xmlStrToObject(XmlRootBean.class, buffer.toString());
        System.out.printf(xmlRootBean.getAppType());
        return "请求服务器成功，时间：" + System.currentTimeMillis();
    }
}
