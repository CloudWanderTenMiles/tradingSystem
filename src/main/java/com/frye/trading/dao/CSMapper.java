package com.frye.trading.dao;

import com.frye.trading.pojo.model.Staff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CSMapper {

    public int checkExist(@Param("params") Map<String, String> params);

    public Staff getStaffByPhone(@Param("phone") String phone);

    public Staff getStaffById(@Param("id") String id);

    public int changePwd(String id, String pwd);

    public int addStaff(Staff staff);

    public int updateStaff(Staff staff);

    public int deleteStaff(String id);

    public List<Staff> getStaffList(int begin, int pageSize, @Param("params") Map<String, String> params);

    public int getCount(@Param("params") Map<String, String> params);

}
