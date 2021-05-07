package com.frye.trading.dao;

import com.frye.trading.pojo.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderMapper {

    public Order getOrderById(@Param("id") String id);

    public int addOrder(Order order);

    public int updateOrder(Order order);

    public int deleteOrder(String id);

    public List<Order> getOrderList(int begin, int pageSize, @Param("params") Map<String, String> params);

    public int getCount(@Param("params") Map<String, String> params);
}
