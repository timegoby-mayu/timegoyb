package com.fly.my.config.filter;

import com.fly.my.Util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2019/9/30$ 9:41$
 * @Version: 1.0
 **/
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {


    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JwtTokenUtil.HEADER_STRING);
        //当token为空或格式错误时 直接放行
        if (header == null || !header.startsWith(JwtTokenUtil.TOKEN_PREFIX)) {
            throw  new RuntimeException ("token 验证失败；");
            //chain.doFilter(request, response);
            //return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(header);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    /**
     * 这里从token中获取用户信息并新建一个token
     */
    /**
     * @author      timegoby
     * @Description 方法描述
     * @return
     * @exception
     * @date        2019/9/29
     */
    private UsernamePasswordAuthenticationToken getAuthentication(String header) {

        String token = header.replace(JwtTokenUtil.TOKEN_PREFIX, "");

        Claims  claims= JwtTokenUtil.generateToken(token);
        String user = claims.getSubject ();//获取用户编码
        List<String> roles = claims.get("role", List.class);
        List<SimpleGrantedAuthority> grantedAuthority = roles.stream().map(s -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, grantedAuthority);
        }
        return null;
    }
}
