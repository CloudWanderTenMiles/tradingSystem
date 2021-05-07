package com.frye.trading.controller;

import com.frye.trading.config.AdminRealm;
import com.frye.trading.pojo.model.Admin;
import com.frye.trading.service.AdminService;
import com.frye.trading.utils.DataJsonUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;

    @RequestMapping("/editPwd")
    @ResponseBody
    public String changePwd(@RequestBody Map<String, String> map) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        //MD5盐值加密
        String oldPwd = AdminRealm.getEncryptedPassword(admin.getAdminName(),map.get("oldPassword"));
        String newPwd = AdminRealm.getEncryptedPassword(admin.getAdminName(), map.get("newPassword"));
        DataJsonUtils dataJsonUtils = new DataJsonUtils();
        if (!oldPwd.equals(admin.getAdminPwd())) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("the old password error!");
            return dataJsonUtils.toString();
        }
        if (adminService.changePassword(admin.getAdminId(),newPwd) < 0) {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("change password error!");
        } else {
            dataJsonUtils.setCode(100);
            dataJsonUtils.setMsg("change password successfully!");
        }
        return dataJsonUtils.toString();
    }

    /**
     * 获取统计页
     * @param model
     * @return
     */
    @RequestMapping("/static")
    public String getAdminHomePage(Model model){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("name",admin.getAdminName());
        return "/admin/static";
    }
}
