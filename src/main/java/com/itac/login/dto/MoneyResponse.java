package com.itac.login.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class MoneyResponse implements Serializable {
    private String currency_name;
    private String price;
    private String before_price;
    private String before_price_percent;
    private String buy;
    private String sell;
    private String send;
    private String receive;
    private String dates;

    @Builder
    public MoneyResponse(
              String currency_name
            , String price
            , String before_price
            , String before_price_percent
            , String buy
            , String sell
            , String send
            , String receive
    ) {
        this.currency_name = currency_name;
        this.price = price;
        this.before_price = before_price;
        this.before_price_percent = before_price_percent;
        this.buy = buy;
        this.sell = sell;
        this.send = send;
        this.receive = receive;
    }
}
