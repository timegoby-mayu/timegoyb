package com.fly.my.controller;

import com.fly.my.Util.execl.ExcelKitServiceUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

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

    @RequestMapping("/downLoad")
    public String download(){
        Map<String,String> title = new LinkedHashMap<String,String>();
        title.put("filedOne","第一列");
        title.put("filedTow","第二列");


        List<Map<String,Object>> data = new ArrayList<>();
        Map<String,Object> object = new HashMap<String,Object>();
        object.put("filedOne","sfesfsefs");
        object.put("filedTow","esefefe");
        data.add(object);
        try {
            ExcelKitServiceUtil.doExportExcel(title, data, false, "excelName", "") ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "请求服务器成功，时间：" + System.currentTimeMillis ();
    }
}
