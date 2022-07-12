package com.jondeflo.artplan.config;

import com.jondeflo.artplan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserService userService;
    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .csrf()
            .disable()
            .authorizeRequests()
            .antMatchers("/register").permitAll()
            .antMatchers("/check/*").permitAll()
            .antMatchers("/logout").permitAll()
            .antMatchers("/login").permitAll()
            .antMatchers("/pet/info/*").permitAll()
            .anyRequest()
            .authenticated()
            .and()
            .logout()
            .invalidateHttpSession( true )
            .deleteCookies( "JSESSIONID" )
            .and()
            .httpBasic();
    }
}
