package com.fly.my.config;

import com.fly.my.config.filter.JwtAuthenticationFilter;
import com.fly.my.config.filter.JwtLoginFilter;
import com.fly.my.config.handler.AuthEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecutiryConfig extends WebSecurityConfigurerAdapter {

    /**
     * 自定义的加密算法
     *
     * @return
     */
    @Bean
    public PasswordEncoder myPasswordEncoder() {
        return new BCryptPasswordEncoder ();
    }

    /**
     * @Author: timegoby
     * @Description: 配置userDetails的数据源，密码加密格式
     * @Date: 2019/3/28-9:24
     * @Param: [auth]
     * @return: void
     **/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
         *自定义认证
         */
        auth.authenticationProvider (userAuthenticationProvider ());
        //auth.authenticationProvider (userAuthenticationProvider ());
        /*内存认证*/
        //auth.inMemoryAuthentication ().withUser ("admin").password (myPasswordEncoder().encode ("p@ssw0rd")).roles ("USER");
        //auth.userDetailsService(userDetailsService).passwordEncoder(myPasswordEncoder());
    }


    private UserAuthenticationProvider userAuthenticationProvider() {
        return new UserAuthenticationProvider ();
    }

    /**
     * @Author: timegoby
     * @Description: 配置放行的资源
     * @Date: 2019/3/28-9:23
     * @Param: [web]
     * @return: void
     **/
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring ().antMatchers ("/index.html", "/static/**", "/loginPage", "/register");
    }

    /**
     * @Author: timegoby
     * @Description: HttpSecurity包含了原数据（主要是url）
     * 通过authenticationEntryPoint集成
     * @Date: 2019/3/27-17:41
     * @Param: [http]
     * @return: void
     **/
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests ().anyRequest ().authenticated ()
                .and ()
                //.addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .formLogin ().loginProcessingUrl ("/login")
                //.usernameParameter ("username").passwordParameter ("password")
                //.failureHandler(new MyAuthenticationFailureHandler())
                //.successHandler(new MyAuthenticationSuccessHandler())
                .and ()//.antMatcher ("/oauth/authorize")
                .logout ()
                .logoutUrl ("/logout")
                //.logoutSuccessHandler(new MyLogoutSuccessHandler())
                .permitAll ()
                .and ().csrf ().disable ()
                .sessionManagement ().sessionCreationPolicy (SessionCreationPolicy.STATELESS);
                //.addFilter (new JwtLoginFilter (authenticationManager ()))
                //.addFilter (new JwtAuthenticationFilter (authenticationManager ()))
                //.exceptionHandling().authenticationEntryPoint(authEntryPoint);
               ;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


}
