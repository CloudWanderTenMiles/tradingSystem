package com.frye.trading.dao;

import com.frye.trading.pojo.dto.Commodity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CommodityMapper {

    public String getCommodityState(@Param("id") String id);

    public Commodity getCommodityById(@Param("id") String id);

    public int addCommodity(Commodity commodity);

    public int updateCommodity(Commodity commodity);

    public int deleteCommodity(String id);

    public List<Commodity> getCommodityList(int begin, int pageSize, @Param("params") Map<String, String> params);

    public int getCount(@Param("params") Map<String, String> params);

    public String getBuyerName(@Param("id") String id);
}
