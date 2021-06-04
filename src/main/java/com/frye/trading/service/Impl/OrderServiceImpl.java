package com.frye.trading.service.Impl;

import com.frye.trading.dao.OrderMapper;
import com.frye.trading.pojo.model.Order;
import com.frye.trading.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Override
    public Order getOrderById(String id) {
        return orderMapper.getOrderById(id);
    }

    @Override
    public int addOrder(Order order) {
        return orderMapper.addOrder(order);
    }

    @Override
    public int updateOrder(Order order) {
        return orderMapper.updateOrder(order);
    }

    @Override
    public int deleteOrder(String id) {
        return orderMapper.deleteOrder(id);
    }

    @Override
    public List<Order> getOrderList(int pageNo, int pageSize, Map<String, String> params) {
        int begin = (pageNo-1) * pageSize;
        return orderMapper.getOrderList(begin, pageSize, params);
    }

    @Override
    public int getCount(Map<String, String> params) {
        return orderMapper.getCount(params);
    }
}
