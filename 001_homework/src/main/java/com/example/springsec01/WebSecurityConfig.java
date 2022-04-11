package com.example.springsec01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collections;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final TestApi testApi;




    @Autowired
    public WebSecurityConfig(TestApi testApi) {

        this.testApi = testApi;
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandlerHandler() {
        return new CustomAuthenticationSuccessHandler(testApi);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        User userAdmin = new User("Jan"
                , getPasswordEncoder().encode("Jan123")
                , Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        User userUser = new User("Karol"
                , getPasswordEncoder().encode("Karol123")
                , Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));


        auth.inMemoryAuthentication().withUser(userAdmin);
        auth.inMemoryAuthentication().withUser(userUser);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/forAdmin").hasRole("ADMIN")
                .antMatchers("/forUser").hasAnyRole("ADMIN","USER")
                .antMatchers("/forUnknown").permitAll()
                .and().formLogin().permitAll()
                .successHandler(authenticationSuccessHandlerHandler())
                .and().logout().logoutSuccessUrl("/bye");

    }
}
