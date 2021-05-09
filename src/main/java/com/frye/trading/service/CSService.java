package com.frye.trading.service;

import com.frye.trading.pojo.model.Staff;
import java.util.List;
import java.util.Map;

public interface CSService {

    public boolean checkExist(String phone, String email);

    public Staff getStaffByPhone(String phone);

    public Staff getStaffById(String id);

    public int changePwd(String id, String pwd);

    public int addStaff(Staff staff);

    public int updateStaff(Staff staff);

    public int deleteStaff(String id);

    public List<Staff> getStaffList(int pageNo, int pageSize, Map<String, String> params);

    public int getCount(Map<String, String> params);
}
