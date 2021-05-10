package com.frye.trading.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;


/**
 * 负责admin、customer、customer service的注销功能
 */


@Controller
public class LogoutController {

    @RequestMapping("/admin/logout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/admin";
    }

    @RequestMapping("/cstaff/logout")
    public String cstaffLogout(HttpSession session) {
        session.removeAttribute("staff");
        return "redirect:/cstaff";
    }
}
