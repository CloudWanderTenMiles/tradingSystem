package com.frye.trading.controller;

import com.frye.trading.pojo.model.Shopcart;
import com.frye.trading.service.ShopcartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ShopcartController {

    @Autowired
    ShopcartService shopcartService;



}
