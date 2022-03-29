package pl.boniaszczuk.springsecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    TestApi testApi;

    @Autowired
    public CustomAuthenticationSuccessHandler(TestApi testApi) {
        this.testApi = testApi;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException {


        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        System.out.println("The user " + username + " has logged in)");
        System.out.println(userDetails.getAuthorities());
        if(userDetails.getAuthorities().toString().equals("[ROLE_ADMIN]")){
            testApi.addAdminCount();
        }else if(userDetails.getAuthorities().toString().equals("[ROLE_USER]")){
            testApi.addUserCount();}
        response.sendRedirect(request.getContextPath());
    }



}
