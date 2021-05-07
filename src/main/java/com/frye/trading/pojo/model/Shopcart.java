package com.frye.trading.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shopcart {

    private String customerId;
    private String commodityId;
    private String state;
}
