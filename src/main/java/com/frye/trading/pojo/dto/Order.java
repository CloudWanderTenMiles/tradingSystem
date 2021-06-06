package com.frye.trading.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private String orderId;
    private String state;
    private String commodityName;  // 商品名称
    private double price;          // 商品总价
    private String customerName;   // 所属用户名
}
