package com.frye.trading.controller;

import com.frye.trading.config.CustomerRealm;
import com.frye.trading.pojo.model.Customer;
import com.frye.trading.service.CustomerService;
import com.frye.trading.utils.DataJsonUtils;
import com.frye.trading.utils.GenerateIdUtils;

import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理对于customer的各种操作
 */

@Controller
public class CustomerController {

    @Autowired
    CustomerService customerService;

    /**
     * 管理员界面查询顾客
     *
     * @param page         table页码
     * @param limit        table每张表的行数
     * @param customerId   顾客id
     * @param customerName 顾客昵称
     * @param realName     顾客名称
     * @param phone        手机号
     * @param email        邮箱
     * @return json字符串
     */
    @RequestMapping(value = "/op/customerList", method = RequestMethod.POST)
    @ResponseBody
    public String getCustList(@Param("page") int page, @Param("limit") int limit, @Param("customerId") String customerId, @Param("customerName") String customerName,
                              @Param("realName") String realName, @Param("phone") String phone, @Param("email") String email) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("customerId", customerId);
        params.put("customerName", customerName);
        params.put("realName", realName);
        params.put("phone", phone);
        params.put("email", email);
        List<Customer> customerList = customerService.getCustomerList(page, limit, params);
        int count = customerService.getCount(params);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (count >= 0) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(count);
            dataJsonUtils.setData(customerList);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data failed");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 增加一个用户
     *
     * @param map 用户信息
     * @return 增加结果
     * @throws ParseException 解析异常
     */
    @RequestMapping(value = "/op/customerAdd", method = RequestMethod.POST)
    @ResponseBody
    public String addCustomer(@RequestBody Map<String, String> map) throws ParseException {
        // 检测stuid、phone、email、name是否满足unique约束
        String stuId = map.get("studentId");
        String phone = map.get("phone");
        String email = map.get("email");
        String customerName = map.get("customerName");
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (customerService.checkExist(stuId, null, null, null)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The student ID has been used!");
            return dataJsonUtils.toString();
        }
        if (customerService.checkExist(null, phone, null, null)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The phone has been used!");
            return dataJsonUtils.toString();
        }
        if (customerService.checkExist(null, null, email, null)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The email has been used!");
            return dataJsonUtils.toString();
        }
        if (customerService.checkExist(null, null, null, customerName)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The name has been used!");
            return dataJsonUtils.toString();
        }
        // 生成无重复的customer ID
        Customer customer = new Customer();
        String id = GenerateIdUtils.generateCustID();
        while (customerService.getCustomerById(id) != null) {
            id = GenerateIdUtils.generateCustID();
        }
        // 设置customer的成员
        customer.setCustomerId(id);
        customer.setCustomerName(customerName);
        customer.setRealName(map.get("realName"));
        customer.setStudentId(stuId);
        customer.setCustomerPwd(CustomerRealm.getEncryptedPassword(phone, map.get("customerPwd")));
        customer.setSex(map.get("sex"));
        customer.setBirth(map.get("birth"));
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(map.get("address"));
        customer.setCredit(Double.parseDouble(map.get("credit")));
        customer.setPhoto("/image/default.jpg");
        customer.setRole("customer");
        if (customerService.addCustomer(customer) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("add customer error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("add customer successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 删除customer
     *
     * @return json
     */
    @RequestMapping(value = "/op/customerDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCustomer(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for (String id : idList) {
            if (customerService.deleteCustomer(id) < 0) {
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
     * 跳转到修改页面
     *
     * @param id    修改的customer id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping("/admin/customerUpdate/{id}")
    public String toUpdatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/admin/customerUpdate";
    }

    /**
     * 跳转到用户页
     *
     * @param id    customer id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping("/mall/personCenter/{id}")
    public String topersonCenterPage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/mall/personCenter";
    }

    /**
     * 从后台获取customer的信息填充到form中
     *
     * @return 返回customer的json
     */
    @RequestMapping("/op/getCustomer1")
    @ResponseBody
    public String getCustomer1(@RequestBody String customerId) {
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        Customer customer1 = customerService.getCustomerById(customerId);
        if (customer1 != null) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setData(customer1);
            dataJsonUtils.setMsg("successfully");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("Error");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 聊天界面获取customer的image
     *
     * @return 返回customer的json
     */
    @RequestMapping("/op/getCustomerPhoto")
    @ResponseBody
    public String getCustomerPhotoByName(@RequestBody String name) {
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        String photo = customerService.getCustomerPhotoByName(name);
        if (photo != null) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setData(photo);
            dataJsonUtils.setMsg("successfully");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("Error");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 更改customer
     *
     * @param map customer信息
     * @return 返回更改结果
     * @throws ParseException 解析异常
     */
    @RequestMapping(value = "/op/customerUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String updateCustomer(@RequestBody Map<String, String> map) throws ParseException {
        // 通过id获取原有的customer
        String customerId = map.get("customerId");
        Customer customer = customerService.getCustomerById(customerId);
        // 检测stuid、phone、email是否满足unique约束
        String stuId = map.get("studentId");
        String phone = map.get("phone");
        String email = map.get("email");
        String customerName = map.get("customerName");
        // 如果stuid、phone、email已更改，且存在于数据库中
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (!stuId.equals(customer.getStudentId()) && customerService.checkExist(stuId, null, null, null)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The student ID has been used!");
            return dataJsonUtils.toString();
        }
        if (!phone.equals(customer.getPhone()) && customerService.checkExist(null, phone, null, null)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The phone has been used!");
            return dataJsonUtils.toString();
        }
        if (!email.equals(customer.getEmail()) && customerService.checkExist(null, null, email, null)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The email has been used!");
            return dataJsonUtils.toString();
        }
        if (!customerName.equals(customer.getCustomerName()) && customerService.checkExist(null, null, email, customerName)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The name has been used!");
            return dataJsonUtils.toString();
        }
        // 设置customer的成员
        customer.setCustomerName(customerName);
        customer.setRealName(map.get("realName"));
        customer.setStudentId(stuId);
        customer.setCustomerPwd(CustomerRealm.getEncryptedPassword(phone, map.get("customerPwd")));
        customer.setSex(map.get("sex"));
        customer.setBirth(map.get("birth"));
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(map.get("address"));
        customer.setCredit(Double.parseDouble(map.get("credit")));
        if (customerService.updateCustomer(customer) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("update customer error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("update customer successfully!");
        }
        return dataJsonUtils.toString();
    }

    @RequestMapping("/op/changePwd")
    @ResponseBody
    public String changePwd(@RequestBody Map<String, String> map) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        //MD5盐值加密
        String oldPwd = CustomerRealm.getEncryptedPassword(customer.getPhone(), map.get("oldPassword"));
        String newPwd = CustomerRealm.getEncryptedPassword(customer.getPhone(), map.get("newPassword"));
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (!oldPwd.equals(customer.getCustomerPwd())) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("the old password error!");
            return dataJsonUtils.toString();
        }
        if (newPwd.equals(customer.getCustomerPwd())) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The new password cannot be the same as the old password!");
            return dataJsonUtils.toString();
        }
        if (customerService.changePwd(customer.getCustomerId(), newPwd) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("change password error!");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("change password successfully!");
        }
        return dataJsonUtils.toString();
    }
}
