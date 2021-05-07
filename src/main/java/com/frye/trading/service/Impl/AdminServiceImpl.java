package com.frye.trading.service.Impl;

import com.frye.trading.dao.AdminMapper;
import com.frye.trading.pojo.model.Admin;
import com.frye.trading.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin getAdminByID(String id) {
        return adminMapper.queryAdminById(id);
    }

    @Override
    public int changePassword(String id, String password) {
        return adminMapper.changePassword(id, password);
    }
}
