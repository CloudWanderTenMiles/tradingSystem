package com.frye.trading.pojo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private String commodityId;
    private String buyerId;
    private String sellerId;
    private String buyerScore;
    private String sellerScore;
    private String state;
}
