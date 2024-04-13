package com.ticlivetest.control;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/kakaoapi")
public class KakaoApiController {
    @GetMapping("/map")
    public String kakao_map(){
        return "api_test/api_test";
    }
}
