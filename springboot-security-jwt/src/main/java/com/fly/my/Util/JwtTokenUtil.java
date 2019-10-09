package com.fly.my.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;

import java.io.PrintWriter;
import java.util.Date;
import java.util.stream.Collectors;

public class JwtTokenUtil {

    public static final String TOKEN_PREFIX = "Bearer"; //token 开头
    public static final String HEADER_STRING = "Authorization"; //token 类型key
    public static final String secret = "MyJwtSecret11";
    public static final Long expiration = System.currentTimeMillis () + 60 * 60 * 24 * 1000;//失效一天


    /**
     * @return
     * @throws
     * @author timegoby
     * @Description 生成token
     * @date 2019/9/30
     */
    public static String createToken(Authentication authResult) {
        Claims claims = Jwts.claims ();
        claims.put ("role", authResult.getAuthorities ().stream ().map (s -> s.getAuthority ()).collect (Collectors.toList ()));
        String token = Jwts.builder ()
                .setClaims (claims)
                .setSubject (authResult.getName ())
                .setExpiration (new Date (expiration))
                        .signWith (SignatureAlgorithm.HS512, secret).compact ();
        System.out.println (TOKEN_PREFIX+token);
        return TOKEN_PREFIX+token;
    }

    /**
     * 解析token
     *
     * @param token
     * @return
     */
    public static Claims generateToken(String token) {
        return Jwts.parser ()
                .setSigningKey (secret)
                .parseClaimsJws (token)
                .getBody ();
    }
}
