package pl.boniaszczuk.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.util.Collections;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    TestApi testApi;

    @Autowired
    public WebSecurityConfig(TestApi testApi) {
        this.testApi = testApi;
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandlerHandler() {
        return new CustomAuthenticationSuccessHandler(testApi);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {


        User userAdmin = new User("Jan"
                ,getPasswordEncoder().encode("Jan123")
                , Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN")));
        User userUser = new User("Karol"
                ,getPasswordEncoder().encode("Karol123")
                , Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")));


        auth.inMemoryAuthentication().withUser(userAdmin);
        auth.inMemoryAuthentication().withUser(userUser);

    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/hiAdmin").hasRole("ADMIN")
                .antMatchers("/hiUser").hasAnyRole("USER", "ADMIN")
                .and().formLogin().successHandler(authenticationSuccessHandlerHandler())
                .and().logout().logoutSuccessUrl("/bye");

    }
}
