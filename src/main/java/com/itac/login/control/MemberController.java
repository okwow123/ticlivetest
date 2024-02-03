package com.itac.login.control;

import com.itac.login.service.MemberRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRegisterService memberRegisterService;

    @GetMapping("/login")
    public String getLoginPage(Model model,
                               @RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "exception", required = false) String exception) {
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "login";
    }


    @GetMapping("/login/register")
    public String register() {
        return "register";
    }

    @PostMapping(value = "/login/registerMember")
    public String getLoginPage(HttpServletRequest request, Model model) {
        String username = request.getParameter("username");
        String password = new BCryptPasswordEncoder().encode(request.getParameter("password"));
        String auth = request.getParameter("auth");
        int ret_value = 0;

        try{
            ret_value= memberRegisterService.registerMember(username,password,auth);
        }catch(Exception e){
            model.addAttribute("msg",e.getMessage());
        }

        if(ret_value==1){
            model.addAttribute("msg","회원가입 성공");
        }
        return "login";
    }





}
