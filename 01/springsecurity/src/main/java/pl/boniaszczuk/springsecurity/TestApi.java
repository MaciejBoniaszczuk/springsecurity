package pl.boniaszczuk.springsecurity;


import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class TestApi {

    private int adminCounter = 0;
    private int userCounter = 0;


    @GetMapping("/hiAdmin")
    public String hiAdmin(Principal principal, Authentication authentication){

        return "Cześć admin: "+ principal.getName() +
                "Uwierzytelniłeś się już: "+getCounter(authentication)+" razy";
    }
    @GetMapping("/hiUser")
    public String hiUser(Principal principal, Authentication authentication){
        return "Cześć user: "+principal.getName() +
                "Uwierzytelniłeś się już: "+getCounter(authentication)+" razy";
    }
    @GetMapping("/hiUnknown")
    public String hiUnknown(@Nullable Principal principal,@Nullable Authentication authentication){
        if (principal != null){
            return "Cześć: "+principal.getName()  +
                    "Uwierzytelniłeś się już: "+getCounter(authentication)+" razy";
        }
        return "Cześć nieznajomy";
    }
    @GetMapping("/bye")
    public String bye(){
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
