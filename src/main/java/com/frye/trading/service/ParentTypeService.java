package com.frye.trading.service;

import com.frye.trading.pojo.model.ParentType;

import java.util.List;

public interface ParentTypeService {

    public List<ParentType> getAllParentType();

    public List<ParentType> getParentTypeList(int pageNo, int pageSize, String parentTypeName);

    public int getCount(String parentTypeName);
}
