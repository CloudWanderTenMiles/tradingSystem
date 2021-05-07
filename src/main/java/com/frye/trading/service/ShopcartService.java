package com.frye.trading.service;

import com.frye.trading.pojo.model.Shopcart;

import java.util.List;

public interface ShopcartService {

    public List<Shopcart> getCommodityByCustomerId(String id);

    public int addShopcart(String customerId, String commodityId);

    public int deleteShopcart(String customerId, String commodityId);

    public int setAllShopcartInvalidByCommodityId(String commodityId);

}
