package com.frye.trading.controller;

import com.frye.trading.pojo.dto.Commodity;
import com.frye.trading.pojo.model.Customer;
import com.frye.trading.pojo.model.Order;
import com.frye.trading.service.CommodityService;
import com.frye.trading.service.CustomerService;
import com.frye.trading.service.OrderService;
import com.frye.trading.service.ShopcartService;
import com.frye.trading.utils.DataJsonUtils;
import com.frye.trading.utils.GenerateIdUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    CommodityService commodityService;
    @Autowired
    CustomerService customerService;
    @Autowired
    ShopcartService shopcartService;


    /**
     * 获取订单列表
     * @param page            页码
     * @param limit           每页数据条数
     * @param orderId         订单id
     * @param commodityId     商品id
     * @param buyerId         买方id
     * @param sellerId        卖方id
     * @param state           订单状态
     * @return                order list的json字符串
     */
    @RequestMapping("/op/orderList")
    @ResponseBody
    public String getOrderList(@Param("page") int page, @Param("limit") int limit, @Param("orderId") String orderId, @Param("commodityId") String commodityId,
                               @Param("buyerId") String buyerId, @Param("sellerId") String sellerId, @Param("state") String state) {
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("orderId", orderId);
        params.put("commodityId", commodityId);
        params.put("buyerId", buyerId);
        params.put("sellerId", sellerId);
        params.put("state", state);
        List<Order> orders = orderService.getOrderList(page, limit, params);
        int count = orderService.getCount(params);
        if (count >= 0) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(count);
            dataJsonUtils.setData(orders);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data failed");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 增加order
     * @param map order信息
     * @return    增加结果
     */
    @RequestMapping(value = "/op/orderAdd", method = RequestMethod.POST)
    @ResponseBody
    public String addOrder(@RequestBody Map<String, String> map) {
        String commodityId = map.get("commodityId");
        String buyerId = map.get("buyerId");
        String sellerId = map.get("sellerId");
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (!commodityService.checkCommodity(commodityId)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("the commodity can not be ordered");
            return dataJsonUtils.toString();
        }
        if (customerService.getCustomerById(buyerId) == null) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("the buyer does not exist");
            return dataJsonUtils.toString();
        }
        if (customerService.getCustomerById(sellerId) == null) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("the seller does not exist");
            return dataJsonUtils.toString();
        }

        Order order = new Order();
        String orderId = GenerateIdUtils.generateOrderID();
        while (orderService.getOrderById(orderId) != null) {
            orderId = GenerateIdUtils.generateOrderID();
        }
        order.setOrderId(orderId);
        order.setCommodityId(commodityId);
        order.setBuyerId(buyerId);
        order.setSellerId(sellerId);
        order.setState("Ordered");
        if (orderService.addOrder(order) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("add order error! please check the data you enter.");
        } else {
            Commodity commodity = commodityService.getCommodityById(commodityId);
            commodity.setState("2");
            shopcartService.deleteShopcart(buyerId, commodityId);
            shopcartService.setAllShopcartInvalidByCommodityId(commodityId);
            commodityService.updateCommodity(commodity);
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("add order successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 删除order
     * @return json
     */
    @RequestMapping(value = "/op/orderDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOrder(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for (String id : idList){
            if (orderService.deleteOrder(id) < 0){
                error = true;
            }
        }
        if (error) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("delete error");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("delete successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 撤销order
     * @return json
     */
    @RequestMapping(value = "/op/orderWithdraw", method = RequestMethod.POST)
    @ResponseBody
    public String withdrawOrder(@RequestBody Map<String, String> map) {
        String orderId = map.get("orderId");
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Order order = orderService.getOrderById(orderId);
        //order.setOrderId(orderId);
        order.setState("withdrawn");
        if (orderService.updateOrder(order) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("withdraw error");
        } else {
            String commodityId = orderService.getOrderById(orderId).getCommodityId();
            Commodity commodity = commodityService.getCommodityById(commodityId);
            commodity.setState("1");
            shopcartService.setAllShopcartValidByCommodityId(commodityId);
            commodityService.updateCommodity(commodity);
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("withdraw successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 完成order
     * @return json
     */
    @RequestMapping(value = "/op/orderComplete", method = RequestMethod.POST)
    @ResponseBody
    public String completeOrder(@RequestBody Map<String, String> map) {
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        String orderId = map.get("orderId");
        Order order = orderService.getOrderById(orderId);
        String commodityId = order.getCommodityId();
        if (commodityService.checkCommodityComplete(commodityId)) {
            order.setState("completed");
            if (orderService.updateOrder(order) < 0) {
                dataJsonUtils.setCode(100);
                dataJsonUtils.setMsg("complete error!");
            } else {
                Commodity commodity = commodityService.getCommodityById(commodityId);
                commodity.setState("4");
                commodityService.updateCommodity(commodity);
                dataJsonUtils.setCode(200);
                dataJsonUtils.setMsg("complete successfully!");
            }
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("wait seller confirm!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 跳转到修改页面
     * @param id 修改的order id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping( "/admin/orderUpdate/{id}")
    public String toUpdatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/admin/orderUpdate";
    }

    /**
     * 跳转到投诉
     * @param orderId 修改的order id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping( "/mall/myComplaint/{orderId}")
    public String toMycomplaintPage(@PathVariable("orderId") String orderId, Model model) {
        model.addAttribute(orderId);
        return "/mall/myComplaint";
    }

    /**
     * 从后台获取order的信息填充到form中
     * @param id order id
     * @return 返回order的json
     */
    @RequestMapping(value = "/op/getOrder", method = RequestMethod.POST)
    @ResponseBody
    public String getCustomer(@RequestBody String id) {
        Order order = orderService.getOrderById(id);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (order != null) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setData(order);
            dataJsonUtils.setMsg("Pull data successfully");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("Error pulling data");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 修改order
     * @param map order信息
     * @return    修改的结果
     */
    @RequestMapping(value = "/op/orderUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String updateOrder(@RequestBody Map<String, String> map) {
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Order order = new Order();
        order.setOrderId(map.get("orderId"));
        order.setState(map.get("state"));
        order.setBuyerScore(map.get("buyerScore"));
        order.setSellerScore(map.get("buyerScore"));
        if (orderService.updateOrder(order) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("update order error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("update order successfully!");
        }
        return dataJsonUtils.toString();
    }

    @RequestMapping("/mall/myOrder")
    public String getMyOrder(Model model) {
        Subject subject = SecurityUtils.getSubject();
        Customer customer = (Customer) subject.getSession().getAttribute("customer");
        model.addAttribute("customerId", customer.getCustomerId());
        return "/mall/myOrder";
    }

    @RequestMapping("/op/ordermy")
    @ResponseBody
    public String getMyOrderList(@Param("buyerId") String buyerId, @Param("state") String state) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("buyerId", buyerId);
        params.put("state", state);
        List<Order> orders = orderService.getOrderList(1, 100, params);
        List<com.frye.trading.pojo.dto.Order> myOrders = new ArrayList<>();
        for (Order order : orders) {
            Commodity commodity = commodityService.getCommodityById(order.getCommodityId());
            com.frye.trading.pojo.dto.Order myOrder = new com.frye.trading.pojo.dto.Order();
            myOrder.setOrderId(order.getOrderId());
            myOrder.setCommodityName(commodity.getCommodityName());
            myOrder.setPrice(commodity.getPrice());
            myOrder.setCustomerName(commodity.getCustomerName());
            myOrder.setState(order.getState());
            myOrders.add(myOrder);
        }
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        dataJsonUtils.setCode(200);
        dataJsonUtils.setMsg("get data successfully");
        dataJsonUtils.setData(myOrders);
        return dataJsonUtils.toString();
    }
}
