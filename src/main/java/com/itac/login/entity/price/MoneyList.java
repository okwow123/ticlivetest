package com.itac.login.entity.price;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="crawlingMoneyExchange")
@Getter
@DynamicUpdate
@NoArgsConstructor
public class MoneyList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column
    private String currency_name;
    @Column
    private String price;
    @Column
    private String before_price;
    @Column
    private String before_price_percent;
    @Column
    private String buy;
    @Column
    private String sell;
    @Column
    private String send;
    @Column
    private String receive;
    @Column
    private String dates;


    @Builder
    public MoneyList(String currency_name, String price, String before_price
            , String before_price_percent, String buy, String sell, String send, String receive,String dates
    ) {
        this.currency_name = currency_name;
        this.price = price;
        this.before_price = before_price;
        this.before_price_percent = before_price_percent;
        this.buy = buy;
        this.sell = sell;
        this.send = send;
        this.receive = receive;
        this.dates = dates;
    }

}
