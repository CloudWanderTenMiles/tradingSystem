package com.frye.trading.service;

import com.frye.trading.pojo.model.Type;

import java.util.List;
import java.util.Map;

public interface TypeService {

    public Type getTypeById(String id);

    public int addType(Type type);

    public int updateType(Type type);

    public int deleteType(String id);

    public List<Type> getAllTypeByParentTypeId(int parentTypeId);

    public List<Type> getTypeList(int pageNo, int pageSize, Map<String, String> params);

    public int getCount(Map<String, String> params);
}
