package com.fly.my.Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;


@Slf4j
public class ResponseUtil {

    public static void write(HttpServletResponse response, Object o) {
        try {
            ObjectMapper objectMapper = new ObjectMapper ();
            response.setContentType ("application/json; charset=utf-8");
            PrintWriter out = response.getWriter ();
            //json返回
            out.println (objectMapper.writeValueAsString (o));
            out.flush ();
            out.close ();
        } catch (Exception e) {
            log.error ("e={}", e);
        }
    }
}
