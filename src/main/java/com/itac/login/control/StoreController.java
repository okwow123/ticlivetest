package com.itac.login.control;

import com.itac.login.entity.store.Store;
import com.itac.login.service.MemberService;
import com.itac.login.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/api/v1/store")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @Autowired
    private MemberService memberService;

    @GetMapping("/info")
    public String store(HttpServletRequest req, Authentication auth){
        // 추후 AuthSuccessHandler을 통해 제한
        try{
            auth.getAuthorities();
        }catch(Exception e){
            return "redirect:/login";
        }

        String email = req.getUserPrincipal().getName();
        if(!memberService.getAuth(email).equals("store")){
            return "redirect:/mypage";
        }else{
            return "redirect:/storePage";
        }

    }

    @GetMapping("/list")
    public List<Store> list(Model model){

        return null;
    }

    @GetMapping("/store")
    public String store(Model model){
        return null;
    }

    @PostMapping("/create")
    public String Create(@RequestBody Store store){
        return null;
    }


}
