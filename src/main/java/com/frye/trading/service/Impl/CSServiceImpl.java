package com.frye.trading.service.Impl;

import com.frye.trading.dao.CSMapper;
import com.frye.trading.pojo.model.Staff;
import com.frye.trading.service.CSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CSServiceImpl implements CSService {

    @Autowired
    CSMapper csMapper;

    @Override
    public boolean checkExist(String phone, String email) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("phone", phone);
        params.put("email", email);
        return csMapper.checkExist(params) > 0;
    }

    @Override
    public Staff getStaffByPhone(String phone) {
        return csMapper.getStaffByPhone(phone);
    }

    @Override
    public Staff getStaffById(String id) {
        return csMapper.getStaffById(id);
    }

    @Override
    public int changePwd(String id, String pwd) {
        return csMapper.changePwd(id, pwd);
    }

    @Override
    public int addStaff(Staff staff) {
        return csMapper.addStaff(staff);
    }

    @Override
    public int updateStaff(Staff staff) {
        return csMapper.updateStaff(staff);
    }

    @Override
    public int deleteStaff(String id) {
        return csMapper.deleteStaff(id);
    }

    @Override
    public List<Staff> getStaffList(int pageNo, int pageSize, Map<String, String> params) {
        int begin = (pageNo-1) * pageSize;
        return csMapper.getStaffList(begin, pageSize, params);
    }

    @Override
    public int getCount(Map<String, String> params) {
        return csMapper.getCount(params);
    }
}
