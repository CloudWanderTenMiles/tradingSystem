package com.frye.trading.controller;

import com.frye.trading.pojo.model.Complaint;
import com.frye.trading.service.ComplaintService;
import com.frye.trading.service.OrderService;
import com.frye.trading.utils.DataJsonUtils;
import com.frye.trading.utils.GenerateIdUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ComplaintController {

    @Autowired
    ComplaintService complaintService;
    @Autowired
    OrderService orderService;


    /**
     * 获取complaint列表
     * @param page     页码
     * @param limit    数据条数
     * @param orderId  订单id
     * @param state    状态
     * @return         json字符串
     */
    @RequestMapping("/op/complaintList")
    @ResponseBody
    public String getComplaintList(@Param("page") int page, @Param("limit") int limit, @Param("orderId") String orderId,
                                   @Param("state") String state) {
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Map<String, String> params = new LinkedHashMap<>();
        params.put("orderId", orderId);
        params.put("state", state);
        List<Complaint> complaints = complaintService.getComplaintList(page, limit, params);
        int count = complaintService.getCount(params);
        if (count >= 0) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(count);
            dataJsonUtils.setData(complaints);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data failed");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 增加complaint
     * @param map   complaint信息map
     * @return      增加结果
     */
    @RequestMapping("/op/complaintAdd")
    @ResponseBody
    public String addOrder(@RequestBody Map<String, String> map) {
        String orderId = map.get("orderId");
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (orderService.getOrderById(orderId) == null) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("the order does not exist!");
            return dataJsonUtils.toString();
        }

        Complaint complaint = new Complaint();
        String complaintId = GenerateIdUtils.generateComplaintID();
        while (complaintService.getComplaintById(complaintId) != null) {
            complaintId = GenerateIdUtils.generateComplaintID();
        }
        complaint.setComplaintId(complaintId);
        complaint.setOrderId(orderId);
        complaint.setDetail(map.get("detail"));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        complaint.setCreateTime(df.format(new Date().getTime()));
        complaint.setState("Processing");
        if (complaintService.addComplaint(complaint) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("add complaint error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("add complaint successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 删除complaint
     * @param idList id list
     * @return 删除结果
     */
    @RequestMapping(value = "/op/complaintDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteOrder(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for (String id : idList){
            if (complaintService.deleteComplaint(id) < 0){
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
     * 跳转到更改页面
     * @param id      complaint id
     * @param model   Model
     * @return 跳转页面
     */
    @RequestMapping( "/admin/complaintUpdate/{id}")
    public String toUpdatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/admin/complaintUpdate";
    }




    /**
     * 获取complaint信息
     * @param id complaint id
     * @return
     */
    @RequestMapping(value = "/op/getComplaint", method = RequestMethod.POST)
    @ResponseBody
    public String getCustomer(@RequestBody String id) {
        Complaint complaint = complaintService.getComplaintById(id);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (complaint != null) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setData(complaint);
            dataJsonUtils.setMsg("Pull data successfully");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("Error pulling data");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 更改complaint
     * @param map     complaint信息
     * @return        更改结果
     * @throws ParseException 解析异常
     */
    @RequestMapping(value = "/op/complaintUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String updateCustomer(@RequestBody Map<String, String> map) throws ParseException {
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Complaint complaint = new Complaint();
        complaint.setComplaintId(map.get("complaintId"));
        complaint.setState(map.get("state"));
        complaint.setFinishTime(map.get("finishTime"));
        complaint.setDetail(map.get("detail"));
        complaint.setResult(map.get("result"));
        if (complaintService.updateComplaint(complaint) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("update complaint error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("update complaint successfully!");
        }
        return dataJsonUtils.toString();
    }

}
