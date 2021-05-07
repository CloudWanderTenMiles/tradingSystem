package com.frye.trading.service;

import com.frye.trading.pojo.dto.Commodity;

import java.util.List;
import java.util.Map;

public interface CommodityService {

    /**
     * 检测商品是否可下单
     * @param id 商品id
     * @return boolean类型
     */
    public boolean checkCommodity(String id);

    public Commodity getCommodityById(String id);

    public int addCommodity(Commodity commodity);

    public int updateCommodity(Commodity commodity);

    public int deleteCommodity(String id);

    public List<Commodity> getCommodityList(int pageNo, int pageSize, Map<String, String> params);

    public int getCount(Map<String, String> params);
}
