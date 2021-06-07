package com.frye.trading.service;

import com.frye.trading.pojo.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    public Order getOrderById(String id);

    public int addOrder(Order order);

    public int updateOrder(Order order);

    public int deleteOrder(String id);

    public int deleteOrderByCid(String id);

    public List<Order> getOrderList(int pageNo, int pageSize, Map<String, String> params);

    public int getCount(Map<String, String> params);

}
