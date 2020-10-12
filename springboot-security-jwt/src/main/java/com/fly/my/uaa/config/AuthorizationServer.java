package com.fly.my.uaa.config;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

/**
 * @Description: 作用描述
 * @Author: timegoby
 * @Date: 2020/4/1$ 12:52$
 * @Version: 1.0
 **/
//@Configuration
//@EnableAuthorizationServer
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter
{
    @Autowired
    private DataSource dataSource;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * @author timegoby
     * @Description 配置令牌端点安全策略
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception
    {
        security.checkTokenAccess ("permitAll()") // /oauth/token_key
                .tokenKeyAccess ("permitAll()") // /oauth/check_token公开
                .allowFormAuthenticationForClients ();//表单认证，令牌申请
    }

    /**
     * @author timegoby
     * @Description 客户端详情配置
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception
    {
        /*
        将客户端配置信息存在内存中
        * */
        /*clients.inMemory ()//使用in-memory内存
        .withClient ("K2")//client_id
        .secret (new BCryptPasswordEncoder ().encode ("secret"))
        .resourceIds ("res1")//运行访问的资源Id
        .authorizedGrantTypes ("authorization_code","password","client_credentials","implicit","refresh_token")
        .scopes ("all")
        .autoApprove (false)
        .redirectUris ("http://www.baidu.com");*/
         /*
        JDBC方式存储客户端配置信息
        * */
        clients.withClientDetails(new JdbcClientDetailsService(dataSource));
    }

    /**
     * @author timegoby
     * @Description 令牌访问断点配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception
    {
        endpoints.authenticationManager (authenticationManager)//密码模式需要
                 //.authorizationCodeServices (authorizationCodeServices)//授权码模式需要
                 .tokenStore (tokenStore)//令牌管理服务
                 .allowedTokenEndpointRequestMethods (HttpMethod.POST);
    }

    /**
     * @author timegoby
     * @Description 配置令牌服务
     */
    @Bean
    public AuthorizationServerTokenServices tokenServices(){
        DefaultTokenServices services=new DefaultTokenServices ();
        services.setClientDetailsService (clientDetailsService);//客户端信息服务
        services.setSupportRefreshToken (true);
        services.setTokenStore (tokenStore);//令牌存储策略
        services.setAccessTokenValiditySeconds (7200);//令牌默认有效期2个小时
        services.setRefreshTokenValiditySeconds (259200);//刷新令牌默认有效期3天
        return services;
    }

}
