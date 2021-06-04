package com.frye.trading.dao;

import com.frye.trading.pojo.model.Complaint;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ComplaintMapper {

    public Complaint getComplaintById(String id);

    public int addComplaint(Complaint complaint);

    public int updateComplaint(Complaint complaint);

    public int deleteComplaint(String id);

    public List<Complaint> getComplaintList(int begin, int pageSize, @Param("params") Map<String, String> params);

    public int getCount(@Param("params") Map<String, String> params);
}
