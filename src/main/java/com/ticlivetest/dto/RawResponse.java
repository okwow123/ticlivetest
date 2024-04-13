package com.ticlivetest.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class RawResponse implements Serializable {
    private String product;
    private String exchange_place;
    private String delivery_month;
    private String unit;
    private String price;
    private String updown;
    private String updown_percent;
    private String period;
    private String dates;

}
