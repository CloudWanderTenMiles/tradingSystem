package com.frye.trading.service;

import com.frye.trading.pojo.model.Complaint;

import java.util.List;
import java.util.Map;

public interface ComplaintService {

    public Complaint getComplaintById(String id);

    public int addComplaint(Complaint complaint);

    public int updateComplaint(Complaint complaint);

    public int deleteComplaint(String id);

    public List<Complaint> getComplaintList(int pageNo, int pageSize, Map<String, String> params);

    public int getCount(Map<String, String> params);

}
