package com.itac.login.control;


import org.apache.catalina.authenticator.SpnegoAuthenticator.AuthenticateAction;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String test(){
        return "index";
    }
    @GetMapping("/index2")
    public String index2(){
        return "index2";
    }

    @GetMapping("/home")
    public String home(){
        return "home/home";
    }
    @GetMapping("/search")
    public String search(Model model){
        Authentication Authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(Authentication);

        model.addAttribute("user", Authentication);

        return "search/search";
    }
    @GetMapping("/reservation")
    public String reservation(){
        return "reservation/userReservation";
    }
    @GetMapping("/mypage")
    public String mypage(){
        return "mypage/mypage";
    }

    @GetMapping("/mypage/storePage")
    public String mypage2(){
        return "mypage/storePage";
    }

    @GetMapping("/mypage/storeManage")
    public String storeManage(){ return "mypage/store/StoreManage"; }
}
