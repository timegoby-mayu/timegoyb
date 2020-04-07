package com.fly.my.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2020/4/1$ 14:28$
 * @Version: 1.0
 **/
@Configuration
public class TokenCofin
{
    @Bean
    public TokenStore tokenStore(){
        //使用内存令牌（普通令牌）
        return  new InMemoryTokenStore ();
    }
}
