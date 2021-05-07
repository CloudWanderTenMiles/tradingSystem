package com.frye.trading.service;

import com.frye.trading.pojo.model.Admin;

public interface AdminService {

    public Admin getAdminByID(String id);

    public int changePassword(String id, String password);
}
