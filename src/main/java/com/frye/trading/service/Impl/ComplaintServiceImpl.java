package com.frye.trading.service.Impl;

import com.frye.trading.dao.ComplaintMapper;
import com.frye.trading.pojo.model.Complaint;
import com.frye.trading.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ComplaintServiceImpl implements ComplaintService {

    @Autowired
    ComplaintMapper complaintMapper;

    @Override
    public Complaint getComplaintById(String id) {
        return complaintMapper.getComplaintById(id);
    }

    @Override
    public int addComplaint(Complaint complaint) {
        return complaintMapper.addComplaint(complaint);
    }

    @Override
    public int updateComplaint(Complaint complaint) {
        return complaintMapper.updateComplaint(complaint);
    }

    @Override
    public int deleteComplaint(String id) {
        return complaintMapper.deleteComplaint(id);
    }

    @Override
    public List<Complaint> getComplaintList(int pageNo, int pageSize, Map<String, String> params) {
        int begin = (pageNo-1) * pageSize;
        return complaintMapper.getComplaintList(begin, pageSize, params);
    }

    @Override
    public int getCount(Map<String, String> params) {
        return complaintMapper.getCount(params);
    }
}
