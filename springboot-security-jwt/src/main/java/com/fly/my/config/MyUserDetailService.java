package com.fly.my.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Component
public class MyUserDetailService implements UserDetailsService {


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserDetails userDetails = null;
        try {
            if(username.equals ("admin")){
                Collection<GrantedAuthority> authList = getAuthorities();
                return new User("admin", new BCryptPasswordEncoder ().encode("p@ssw0rd"),authList);
            }else{
                throw new RuntimeException ("user is not exit!");
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return userDetails;

        /*
      设置用户和角色需要注意：
      1. commaSeparatedStringToAuthorityList放入角色时需要加前缀ROLE_，而在controller使用时不需要加ROLE_前缀
      2. 放入的是权限时，不能加ROLE_前缀，hasAuthority与放入的权限名称对应即可

        List<UserDetails> userDetailsList = new ArrayList<>();
        userDetailsList.add(User.builder()
                .username("admin")
                .password(new BCryptPasswordEncoder ().encode ("p@ssw0rd"))
                .authorities(AuthorityUtils.commaSeparatedStringToAuthorityList("read,ROLE_ADMIN")).build());

        return userDetailsList.stream()
                .filter(o -> o.getUsername().equals(username))
                .findFirst()
                .orElse(null);*/
    }

    /**  * 获取用户的角色权限,为了降低实验的难度，这里去掉了根据用户名获取角色的步骤     * @param    * @return   */
    private Collection<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority> ();
        authList.add(new SimpleGrantedAuthority("ROLE_USER"));
        authList.add(new SimpleGrantedAuthority ("ROLE_ADMIN"));
        return authList;
    }


}
