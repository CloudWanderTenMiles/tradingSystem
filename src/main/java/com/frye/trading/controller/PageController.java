package com.frye.trading.controller;

import com.frye.trading.pojo.model.Admin;
import com.frye.trading.pojo.model.Customer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 负责页面跳转
 */

@Controller
public class PageController {
    /**
     * 跳转商城首页
     * @return
     */
    @RequestMapping({"/","/index","/home"})
    public String getHomePage() {
        return "/mall/home";
    }

    /**
     * 跳转管理员登录页
     * @return
     */
    @RequestMapping("/admin")
    public String getAdminLoginPage() {
        return "/admin/alogin";
    }

    /**
     * 跳转客服登录页
     * @return
     */
    @RequestMapping("/cstaff")
    public String getCstaffLoginPage() {
        return "/cstaff/login";
    }

    /**
     * 跳转商城登录页
     * @return
     */
    @RequestMapping("/login")
    public String getCustomerLoginPage() {
        return "/mall/login";
    }

    /**
     * 跳转到后台管理页面
     * @param model
     * @return
     */
    @RequestMapping("/admin/home")
    public String getAdminHomePage(Model model){
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("name",admin.getAdminName());
        return "/admin/home";
    }

    /**
     * 页面映射
     * @param page 页面url
     * @return url
     */
    @RequestMapping("/toPage/admin/{page}")
    public String toAdminPage(@PathVariable String page) {
        return "/admin/" + page;
    }

    /**
     * 页面映射
     * @param page 页面url
     * @return url
     */
    @RequestMapping("/toPage/mall/{page}")
    public String toMallPage(@PathVariable String page) {
        return "/mall/" + page;
    }

    @RequestMapping("/toPage/cstaff/{page}")
    public String toCstaffPage(@PathVariable String page) {
        return "/cstaff/" + page;
    }

    @RequestMapping("/mall/chat")
    public String toUserChatPage(Model model) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Customer customer = (Customer) session.getAttribute("customer");
        model.addAttribute("name", customer.getCustomerName());
        model.addAttribute("id", customer.getCustomerId());
        model.addAttribute("image", customer.getPhoto());
        return "/mall/chat";
    }

    @RequestMapping("/admin/chat")
    public String toAdminChatPage(Model model) {
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession();
        Admin admin = (Admin) session.getAttribute("admin");
        model.addAttribute("name", admin.getAdminName());
        model.addAttribute("id", admin.getAdminId());
        model.addAttribute("image","/image/admin.jpg");
        return "/admin/chat";
    }
}
