package com.frye.trading.service.Impl;

import com.frye.trading.dao.ParentTypeMapper;
import com.frye.trading.pojo.model.ParentType;
import com.frye.trading.service.ParentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentTypeServiceImpl implements ParentTypeService {

    @Autowired
    ParentTypeMapper parentTypeMapper;

    @Override
    public List<ParentType> getAllParentType() {
        return parentTypeMapper.getAllParentType();
    }

    @Override
    public List<ParentType> getParentTypeList(int pageNo, int pageSize, String parentTypeName) {
        int begin = (pageNo-1) * pageSize;
        return parentTypeMapper.getParentTypeList(begin,pageSize,parentTypeName);
    }

    @Override
    public int getCount(String parentTypeName) {
        return parentTypeMapper.getCount(parentTypeName);
    }
}
