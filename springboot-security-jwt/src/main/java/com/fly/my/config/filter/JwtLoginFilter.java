package com.fly.my.config.filter;

import com.fly.my.Util.JwtTokenUtil;
import com.fly.my.Util.ResponseUtil;
import com.fly.my.Util.ResultUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    static final String TOKEN_PREFIX = "Bearer";
    static final String HEADER_STRING = "Authorization";

    private AuthenticationManager authenticationManager;


    public JwtLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

/**
  * @author      timegoby
  * @Description 重新UsernamePasswordAuthenticationFilter的attemptAuthentication方法来获取用户、密码进行认证
  * @param null
  * @return
  * @exception
  * @date        2019/9/29
 */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod ().equals ("POST")) {
            throw new AuthenticationServiceException (
                    "Authentication method not supported: " + request.getMethod ());
        }
        String username = obtainUsername (request);
        String password = obtainPassword (request);
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        username = username.trim ();
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken (
                username, password);
        setDetails (request, authRequest);

        return this.authenticationManager.authenticate (authRequest);
    }

    /**
      * @author      timegoby
      * @Description 认证成功生成token返回到客户端Header
      * @param
      * @return      
      * @exception   
      * @date        2019/9/29 
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        Map<String,Object> result =new HashMap<String,Object> ();
        result.put (JwtTokenUtil.HEADER_STRING,JwtTokenUtil.createToken (authResult));
        ResponseUtil.write (response,result);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.write(response, ResultUtil.error(failed.getMessage()).toString ());
    }
}
