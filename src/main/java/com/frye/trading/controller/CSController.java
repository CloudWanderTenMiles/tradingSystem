package com.frye.trading.controller;

import com.frye.trading.config.CstaffRealm;
import com.frye.trading.pojo.model.Staff;
import com.frye.trading.service.CSService;
import com.frye.trading.utils.DataJsonUtils;
import com.frye.trading.utils.GenerateIdUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CSController {

    @Autowired
    CSService csService;

    /**
     * 获取cs列表
     * @param page  页码
     * @param limit 页面数据条数
     * @param staffId staff id
     * @param staffName staff name
     * @param phone phone
     * @param email email
     * @return 包含cs列表的json字符串
     */
    @RequestMapping(value = "/op/cstaffList", method = RequestMethod.POST)
    @ResponseBody
    public String getCSList(@Param("page") int page, @Param("limit") int limit, @Param("staffId") String staffId, @Param("staffName") String staffName,
                              @Param("phone") String phone, @Param("email") String email) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("staffId", staffId);
        params.put("staffName", staffName);
        params.put("phone", phone);
        params.put("email", email);
        List<Staff> csList = csService.getStaffList(page, limit, params);
        int count = csService.getCount(params);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (count >= 0) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("get data successfully");
            dataJsonUtils.setCount(count);
            dataJsonUtils.setData(csList);
        } else {
            dataJsonUtils.setCode(0);
            dataJsonUtils.setMsg("get data failed");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 添加cs
     * @param map 从前端获取的参数
     * @return 返回添加的结果
     * @throws ParseException 抛出一个解析异常
     */
    @RequestMapping(value = "/op/cstaffAdd", method = RequestMethod.POST)
    @ResponseBody
    public String addCS(@RequestBody Map<String, String> map) throws ParseException {
        // 检查phone、email是否满足unique约束
        String phone = map.get("phone");
        String email = map.get("email");
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (csService.checkExist(phone,null)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The phone has been used!");
            return dataJsonUtils.toString();
        }
        if (csService.checkExist(null,email)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The email has been used!");
            return dataJsonUtils.toString();
        }
        // 生成无重复的staff ID
        Staff staff = new Staff();
        String id = GenerateIdUtils.generateCSID();
        while (csService.getStaffById(id) != null) {
            id = GenerateIdUtils.generateCSID();
        }
        // 设置staff的成员
        staff.setStaffId(id);
        staff.setStaffName(map.get("staffName"));
        staff.setStaffPwd(CstaffRealm.getEncryptedPassword(phone,map.get("staffPwd")));
        staff.setSex(map.get("sex"));
        staff.setBirth(map.get("birth"));
        staff.setPhone(phone);
        staff.setEmail(email);
        staff.setAddress(map.get("address"));
        staff.setPhoto("/image/default.jpg");
        staff.setRole("staff");
        if (csService.addStaff(staff) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("add staff error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("add staff successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 删除staff
     * @return 删除结果的json
     */
    @RequestMapping(value = "/op/cstaffDelete", method = RequestMethod.POST)
    @ResponseBody
    public String deleteCS(@RequestBody String[] idList) {
        boolean error = false;
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        for (String id : idList){
            if (csService.deleteStaff(id) < 0){
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
     * @param id 修改的staff id
     * @param model 传递参数
     * @return 页面url
     */
    @RequestMapping( "/admin/cstaffUpdate/{id}")
    public String toUpdatePage(@PathVariable("id") String id, Model model) {
        model.addAttribute(id);
        return "/admin/cstaffUpdate";
    }

    /**
     * 从后台获取customer的信息填充到form中
     * @param id customer id
     * @return 返回customer的json
     */
    @RequestMapping(value = "/op/getCstaff", method = RequestMethod.POST)
    @ResponseBody
    public String getCStaff(@RequestBody String id) {
        Staff staff = csService.getStaffById(id);
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (staff != null) {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setData(staff);
            dataJsonUtils.setMsg("Pull data successfully");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("Error pulling data");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 更改cstaff
     * @param map   staff信息
     * @return      更改结果
     * @throws ParseException 解析异常
     */
    @RequestMapping(value = "/op/cstaffUpdate", method = RequestMethod.POST)
    @ResponseBody
    public String updateCS(@RequestBody Map<String, String> map) throws ParseException {
        // 通过id获取原有的customer
        String staffId = map.get("staffId");
        Staff staff = csService.getStaffById(staffId);
        // 检测phone、email是否满足unique约束
        String phone = map.get("phone");
        String email = map.get("email");
        // 如果phone、email已更改，且存在于数据库中
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (!phone.equals(staff.getPhone()) && csService.checkExist(phone,null)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The phone has been used!");
            return dataJsonUtils.toString();
        }
        if (!email.equals(staff.getEmail()) && csService.checkExist(null,email)) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("The email has been used!");
            return dataJsonUtils.toString();
        }
        staff.setStaffName(map.get("staffName"));
        staff.setStaffPwd(CstaffRealm.getEncryptedPassword(phone,map.get("staffPwd")));
        staff.setSex(map.get("sex"));
        staff.setBirth(map.get("birth"));
        staff.setPhone(phone);
        staff.setEmail(email);
        staff.setAddress(map.get("address"));
        if (csService.updateStaff(staff) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("update staff error! please check the data you enter.");
        } else {
            dataJsonUtils.setCode(200);
            dataJsonUtils.setMsg("update staff successfully!");
        }
        return dataJsonUtils.toString();
    }
}
