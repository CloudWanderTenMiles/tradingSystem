package com.frye.trading.service.Impl;

import com.frye.trading.pojo.model.Shopcart;
import com.frye.trading.service.ShopcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopcartServiceImpl implements ShopcartService {

    @Autowired
    ShopcartService shopcartService;

    @Override
    public List<Shopcart> getCommodityByCustomerId(String id) {
        return shopcartService.getCommodityByCustomerId(id);
    }

    @Override
    public int addShopcart(String customerId, String commodityId) {
        return shopcartService.addShopcart(customerId, commodityId);
    }

    @Override
    public int deleteShopcart(String customerId, String commodityId) {
        return shopcartService.deleteShopcart(customerId, commodityId);
    }

    @Override
    public int setAllShopcartInvalidByCommodityId(String commodityId) {
        return shopcartService.setAllShopcartInvalidByCommodityId(commodityId);
    }
}
