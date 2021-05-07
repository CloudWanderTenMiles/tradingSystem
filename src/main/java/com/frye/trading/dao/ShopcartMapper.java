package com.frye.trading.dao;

import com.frye.trading.pojo.model.Shopcart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ShopcartMapper {

    public List<Shopcart> getCommodityByCustomerId(@Param("id") String id);

    public int addShopcart(@Param("customerId") String customerId, @Param("commodityId") String commodityId);

    public int deleteShopcart(@Param("customerId") String customerId, @Param("commodityId") String commodityId);

    public int setAllShopcartInvalidByCommodityId(@Param("commodityId") String commodityId);
}
