package com.frye.trading;

import com.frye.trading.pojo.dto.Commodity;
import com.frye.trading.pojo.model.Type;
import com.frye.trading.service.CommodityService;
import com.frye.trading.service.CustomerService;
import com.frye.trading.service.ParentTypeService;
import com.frye.trading.service.TypeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@SpringBootTest
class TradingApplicationTests {

    @Autowired
    CustomerService customerService;
    @Autowired
    ParentTypeService parentTypeService;
    @Autowired
    TypeService typeService;
    @Autowired
    CommodityService commodityService;

    @Test
    public void contextLoads() {

        String commodityId = "CD16229502019231";
        Commodity commodity = commodityService.getCommodityById(commodityId);
        System.out.println(commodity);
    }
}
