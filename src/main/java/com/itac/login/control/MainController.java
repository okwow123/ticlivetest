package com.itac.login.control;


import org.springframework.stereotype.Controller;
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
        return "/home/home";
    }
    @GetMapping("/search")
    public String search(){
        return "/search/search";
    }
    @GetMapping("/reservation")
    public String reservation(){
        return "/reservation/reservation";
    }
    @GetMapping("/mypage")
    public String mypage(){
        return "/mypage/mypage";
    }

    @GetMapping("/mypage/storePage")
    public String mypage2(){
        return "/mypage/storePage";
    }
}
