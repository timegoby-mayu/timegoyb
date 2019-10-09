package com.fly.my.config.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2019/9/30$ 10:56$
 * @Version: 1.0
 **/
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType ("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode result = objectMapper.createObjectNode ();
        result.put ("code",1001);
        result.put ("success",false);
        result.put ("msg",authException.getLocalizedMessage ());
        response.getWriter ().write (result.toString ());
    }
}
