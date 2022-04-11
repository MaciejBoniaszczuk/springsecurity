package com.example.springsec01;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestApi {

    private int adminCounter = 0;
    private int userCounter = 0;

    @GetMapping("/forAll")
    public String forAll() {
        return "forAll";
    }

    @GetMapping("/forUser")
    public String forUser(Principal principal, Authentication authentication){
        return "Cześć user: "+principal.getName() +
                " Uwierzytelniłeś się już: "+getCounter(authentication)+" razy";
    }

    @GetMapping("/forAdmin")
    public String forAdmin(Principal principal, Authentication authentication){

        return "Cześć admin: "+ principal.getName() +
                " Uwierzytelniłeś się już: "+getCounter(authentication)+" razy";
    }


    @GetMapping("/forUnknown")
    public String forUnknown(@Nullable Principal principal, @Nullable Authentication authentication) {
        if (principal != null){
            return "Cześć: "+principal.getName()  +
                    " Uwierzytelniłeś się już: "+getCounter(authentication)+" razy";
        }
        return "Cześć nieznajomy";
    }
    @GetMapping("/bye")
    public String bye() {
        return "Papa";
    }

    public int getCounter(Authentication authentication){
        if (authentication.getAuthorities().toString().equals("[ROLE_ADMIN]")){
            return this.adminCounter;
        }else if((authentication.getAuthorities().toString().equals("[ROLE_USER]"))){
            return this.userCounter;
        }
        else return 0;
    }

    public void addAdminCount() {
        this.adminCounter++;
    }

    public void addUserCount() {
        this.userCounter++;
    }
}

