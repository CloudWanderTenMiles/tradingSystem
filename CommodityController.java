package com.frye.trading.controller;

import com.frye.trading.pojo.dto.Commodity;
import com.frye.trading.service.CommodityService;
import com.frye.trading.service.CustomerService;
import com.frye.trading.utils.DataJsonUtils;
import com.frye.trading.utils.GenerateIdUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CommodityController {

    @Autowired
    CommodityService commodityService;
    @Autowired
    CustomerService customerService;


    /**
     * 获取商品列表
     * @param page                 pageNo, 页码
     * @param limit                每页的数据条数
     * @param customerId           顾客ID
     * @param customerName         顾客姓名
     * @param parentTypeName       父类别标签
     * @param commodityName        商品名称
     * @param state                商品状态
     * @return                     json字符串
     */
    @RequestMapping(value = "/op/commodityList", method = RequestMethod.POST)
    @ResponseBody
    public String getCommodityList(@Param("page") int page, @Param("limit") int limit, @Param("customerId") String customerId, @Param("customerName") String customerName,
                                   @Param("parentTypeName") String parentTypeName, @Param("commodityName") String commodityName, @Param("state") String state) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("customerId", customerId);
        params.put("customerName", customerName);
        params.put("parentTypeName", parentTypeName);
        params.put("commodityName", commodityName);
        params.put("state", state);
        List<Commodity> commodities = commodityService.getCommodityList(page, limit, params);
        int count = commodityService.getCount(params);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (count >= 0) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(count);
            dataJsonUtils.setData(commodities);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data failed");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 增加商品 operation
     * @param map 参数map
     * @return 添加结果
     */
    @RequestMapping("/op/commodityAdd")
    @ResponseBody
    public String addCommodity(@RequestBody Map<String, String > map) {
        String customerId = map.get("customerId");
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (customerService.getCustomerById(customerId) == null) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("the customer does not exist.");
            return dataJsonUtils.toString();
        }

        Commodity commodity = new Commodity();
        String commodityId = GenerateIdUtils.generateCommodityID();
        while (commodityService.getCommodityById(commodityId) != null) {
            commodityId = GenerateIdUtils.generateCommodityID();
        }
        commodity.setCommodityId(commodityId);
        commodity.setCommodityName(map.get("commodityName"));
        commodity.setCustomerId(customerId);
        commodity.setCount(Integer.parseInt(map.get("count")));
        commodity.setPrice(Double.parseDouble(map.get("price")));
        commodity.setImage(map.get("image"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        commodity.setAddedTime(df.format(new Date().getTime()));
        commodity.setDetail(map.get("detail"));
        commodity.setTypeId(Integer.parseInt(map.get("typeId")));
        commodity.setState("1");
        if (commodityService.addCommodity(commodity) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("add commodity error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("add commodity successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 删除commodity
     * @param idList id的列表
     * @return 删除结果
     */
    @RequestMapping("/op/commodityDelete")
    @ResponseBody
    public String deleteCommodity(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for (String id : idList){
            if (commodityService.deleteCommodity(id) < 0){
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
     * 获取commodity
     * @param id commodity的id
     * @return 返回commodity的json
     */
    @RequestMapping("/op/getCommodity")
    @ResponseBody
    public String getCustomer(@RequestBody String id) {
        Commodity commodity = commodityService.getCommodityById(id);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (commodity != null) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setData(commodity);
            dataJsonUtils.setMsg("Pull data successfully");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("Error pulling data");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 跳转到修改页面
     * @param id 修改的commodity id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping( "/admin/commodityUpdate/{id}")
    public String toUpdatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/admin/commodityUpdate";
    }

    /**
     * 更新商品
     * @param map 商品信息map
     * @return 更改结果
     */
    @RequestMapping(value = "/op/commodityUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String updateCustomer(@RequestBody Map<String, String> map) {
        String customerId = map.get("customerId");
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (customerService.getCustomerById(customerId) == null) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("the customer does not exist.");
            return dataJsonUtils.toString();
        }
        Commodity commodity = new Commodity();
        commodity.setCommodityId(map.get("commodityId"));
        commodity.setCommodityName(map.get("commodityName"));
        commodity.setCustomerId(customerId);
        commodity.setCount(Integer.parseInt(map.get("count")));
        commodity.setPrice(Double.parseDouble(map.get("price")));
        commodity.setImage(map.get("image"));
        commodity.setDetail(map.get("detail"));
        commodity.setTypeId(Integer.parseInt(map.get("typeId")));
        commodity.setState(map.get("state"));
        if (commodityService.updateCommodity(commodity) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("add commodity error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("add commodity successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 跳转到商品详情页
     * @param id commodity id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping( "/admin/commodityDetail/{id}")
    public String toAdminDetailPage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/admin/commodityDetail";
    }

    /**
     * 跳转到商品详情页
     * @param commodityId commodity id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping( "/mall/commodityDetail")
    public String toMallDetailPage(@RequestBody String commodityId, Model model) {
        model.addAttribute(commodityId);
        return "/mall/commodityDetail";
    }

    @RequestMapping( "/mall/commodityDetail/{id}")
    public String toDetailPage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/mall/commodityDetail";
    }
}
