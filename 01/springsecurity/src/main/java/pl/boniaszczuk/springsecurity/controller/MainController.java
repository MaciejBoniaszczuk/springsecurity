package pl.boniaszczuk.springsecurity.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.boniaszczuk.springsecurity.entity.AppUser;
import pl.boniaszczuk.springsecurity.repo.AppUserRepo;
import pl.boniaszczuk.springsecurity.service.UserService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {



    private UserService userService;


    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")
        public String login(){
            return "login";
        }


    @RequestMapping("/signUp")
    public ModelAndView signUp(){
        return new ModelAndView("registration", "user", new AppUser());
    }

    @RequestMapping("/register")
    public ModelAndView register(AppUser user, HttpServletRequest request){
        userService.addNewUser(user, request);

        return new ModelAndView("redirect:/login");
    }

    @RequestMapping("/verify-token")
    public ModelAndView register(@RequestParam String token) {
        userService.verifyToken(token);
        return new ModelAndView("redirect:/login");
    }


}
