package com.fly.my.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials () == null) {
            logger.debug ("Authentication failed: no credentials provided");

            throw new BadCredentialsException (messages.getMessage (
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }

        String presentedPassword = authentication.getCredentials ().toString ();

        if (!presentedPassword.equals (userDetails.getPassword ())) {
            logger.debug ("Authentication failed: password does not match stored value");

            throw new BadCredentialsException (messages.getMessage (
                    "AbstractUserDetailsAuthenticationProvider.badCredentials",
                    "Bad credentials"));
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        UserDetails loadedUser = myUserDetailService().loadUserByUsername (username);
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException (
                    "UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    private MyUserDetailService myUserDetailService() {
        return new MyUserDetailService ();
    }

}
