package com.frye.trading.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commodity {
    private String commodityId;    // 商品ID
    private String commodityName;  // 商品名称
    private int count;             // 商品数量
    private double price;          // 商品总价
    private String image;          // 商品图片地址
    private String state;          // 商品状态
    private String detail;         // 商品详情
    private String customerId;     // 所属用户id
    private String customerName;   // 所属用户名
    private int parentTypeId;      // 父标签id
    private String parentTypeName; // 父标签名
    private int typeId;            // 标签id
    private String typeName;       // 标签名
    private String addedTime;      // 上架时间
}
