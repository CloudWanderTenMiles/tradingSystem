package com.frye.trading.service.Impl;

import com.frye.trading.dao.CommodityMapper;
import com.frye.trading.pojo.dto.Commodity;
import com.frye.trading.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    CommodityMapper commodityMapper;

    @Override
    public boolean checkCommodity(String id) {
        if (commodityMapper.getCommodityState(id) == null) {
            return false;
        }
        return commodityMapper.getCommodityState(id).equals("1");
    }

    @Override
    public Commodity getCommodityById(String id) {
        return commodityMapper.getCommodityById(id);
    }

    @Override
    public Boolean checkCommodityComplete(String id) {
        return commodityMapper.getCommodityState(id).equals("3");
    }

    @Override
    public int addCommodity(Commodity commodity) {
        return commodityMapper.addCommodity(commodity);
    }

    @Override
    public int updateCommodity(Commodity commodity) {
        return commodityMapper.updateCommodity(commodity);
    }

    @Override
    public int deleteCommodity(String id) {
        return commodityMapper.deleteCommodity(id);
    }

    @Override
    public List<Commodity> getCommodityList(int pageNo, int pageSize, Map<String, String> params) {
        int begin = (pageNo-1) * pageSize;
        return commodityMapper.getCommodityList(begin, pageSize, params);
    }

    @Override
    public int getCount(Map<String, String> params) {
        return commodityMapper.getCount(params);
    }

    @Override
    public String getBuyerName(String id) {
        return commodityMapper.getBuyerName(id);
    }
}
