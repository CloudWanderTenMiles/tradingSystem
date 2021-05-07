package com.frye.trading.dao;

import com.frye.trading.pojo.model.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {
    public Admin queryAdminById(String id);

    public int changePassword(String id, String password);
}
