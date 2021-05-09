package com.frye.trading.controller;

import com.frye.trading.config.UserToken;
import com.frye.trading.pojo.model.Admin;
import com.frye.trading.service.AdminService;
import com.frye.trading.service.CSService;
import com.frye.trading.service.CustomerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * 负责admin、customer、customer service的登录功能
 */

@Controller
public class LoginController {

    @Autowired
    AdminService adminService;
    @Autowired
    CustomerService customerService;
    @Autowired
    CSService csService;


    /**
     * admin用户登录
     * @param user 前端传回的json对象
     * @return 登陆的信息提示
     */
    @ResponseBody
    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public String login(@RequestBody Map<String, String> user){
        String account = user.get("account");
        String password = user.get("password");
        Subject subject = SecurityUtils.getSubject();
        // 封装用户数据
        UserToken token = new UserToken(account, password, "Admin");
        try {
            subject.login(token);
        } catch (UnknownAccountException e) {
            return "Account not exist!";
        } catch (IncorrectCredentialsException e) {
            return "Password error!";
        }
        Admin admin = adminService.getAdminByID(account);
        Session session = subject.getSession();
        session.setAttribute("admin",admin);
        return "success";
    }

}
