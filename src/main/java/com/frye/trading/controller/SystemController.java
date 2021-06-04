package com.frye.trading.controller;

import com.frye.trading.CONSTANT;
import com.frye.trading.service.CSService;
import com.frye.trading.service.CommodityService;
import com.frye.trading.service.CustomerService;
import com.frye.trading.service.OrderService;
import com.frye.trading.utils.DataJsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class SystemController {
    @Autowired
    CustomerService customerService;
    @Autowired
    CSService csService;
    @Autowired
    CommodityService commodityService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/admin/statistics")
    @ResponseBody
    public String getStatistics() {
        Map<String, String> params = new LinkedHashMap<>();
        int cusNumber = customerService.getCount(params);
        int csNumber = csService.getCount(params);
        params.put("state","1");
        int onSaleCom = commodityService.getCount(params);
        params.put("state","Completed");
        int orderNumber = orderService.getCount(params);
        int days = (int) ((new Date().getTime() - CONSTANT.ORIGIN_TIME.getTime()) / (24*60*60*1000));
        Map<String, Integer> res = new LinkedHashMap<>();
        res.put("cusNumber", cusNumber);
        res.put("csNumber", csNumber);
        res.put("onSaleCom", onSaleCom);
        res.put("orderNumber", orderNumber);
        res.put("days", days);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        dataJsonUtils.setCode(200);
        dataJsonUtils.setData(res);
        return dataJsonUtils.toString();
    }
}
