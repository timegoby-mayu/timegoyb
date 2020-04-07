package com.fly.my.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2020/4/2$ 14:06$
 * @Version: 1.0
 **/
//@Configuration
//@EnableResourceServer
public class ResouceServer extends ResourceServerConfigurerAdapter
{
    public static final  String RESOURCE_ID="res1";


    @Autowired
    private TokenStore tokenStore;

    /**
      * @author      timegoby
      * @Description 资源服务
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId (RESOURCE_ID)//资源id
                .tokenServices (tokenServices())//验证令牌
                .stateless (true);

    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers ("/**").access ("#oauth2.hasAnyScope('all')")
        .and ().csrf ().disable ()
        .sessionManagement ().sessionCreationPolicy (SessionCreationPolicy.STATELESS);
    }

    /**
      * @author      timegoby
      * @Description 令牌验证服务
     */
    @Bean
    public ResourceServerTokenServices tokenServices(){
        DefaultTokenServices services = new DefaultTokenServices ();
        services.setTokenStore (tokenStore);
        services.setAccessTokenValiditySeconds(30);
        services.setRefreshTokenValiditySeconds(7200);
       return  services;
    }
}
