package com.frye.trading.controller;

import com.frye.trading.pojo.model.Customer;
import com.frye.trading.pojo.model.Shopcart;
import com.frye.trading.service.ShopcartService;
import com.frye.trading.utils.DataJsonUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShopcartController {

    @Autowired
    ShopcartService shopcartService;

    @RequestMapping(value = "/op/addtoCart", method = RequestMethod.POST)
    @ResponseBody
    public String addToCart(@Param("commodityId") String commodityId) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (shopcartService.addShopcart(customer.getCustomerId(),commodityId) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("add to cart error!");
        }else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("add to cart successfully!");
        }
        return dataJsonUtils.toString();
    }

}
