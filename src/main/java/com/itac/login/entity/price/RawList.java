package com.itac.login.entity.price;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="crawlingExchange")
@Getter
@DynamicUpdate
@NoArgsConstructor
public class RawList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long no;

    @Column
    private String product;
    @Column
    private String exchange_place;
    @Column
    private String delivery_month;
    @Column
    private String unit;
    @Column
    private String price;
    @Column
    private String updown;
    @Column
    private String updown_percent;
    @Column
    private String period;

    @Column
    private String dates;

    @Builder
    public RawList(String product, String exchange_place, String delivery_month
            , String unit, String price, String updown, String updown_percent, String period,String dates
    ) {
        this.product = product;
        this.exchange_place = exchange_place;
        this.delivery_month = delivery_month;
        this.unit = unit;
        this.price = price;
        this.updown = updown;
        this.updown_percent = updown_percent;
        this.period = period;
        this.dates = dates;
    }

}
