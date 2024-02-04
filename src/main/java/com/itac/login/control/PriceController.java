package com.itac.login.control;

import com.itac.login.service.PriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/price")
@RequiredArgsConstructor
public class PriceController {
    private final PriceService priceService;



    @GetMapping("raw")
    public String rawList(Model model) {
        return "price/raw";
    }

    @GetMapping("exchange")
    public String exchangeList(Model model) {
        return "price/exchange";
    }



    @GetMapping("/crawlingRawDate")
    @ResponseBody
    public String getCrawlingRawDate() throws Exception {
        return  priceService.rawDate();
    }

    @GetMapping("/crawlingMoneyDate")
    @ResponseBody
    public String getCrawlingMoneyDate() throws Exception {
        return  priceService.moneyDate();
    }


    @RequestMapping(value = "/raw/siteAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> readRawSearch(@RequestBody String param) {
        return priceService.rawList(param);
    }

    @RequestMapping(value = "/exchange/siteAjax", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> readMoneySearch(@RequestBody String param) {
        return  priceService.moneyList(param);
    }




}


