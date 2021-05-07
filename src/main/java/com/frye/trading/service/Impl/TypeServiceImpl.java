package com.frye.trading.service.Impl;

import com.frye.trading.dao.TypeMapper;
import com.frye.trading.pojo.model.Type;
import com.frye.trading.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeMapper typeMapper;

    @Override
    public Type getTypeById(String id) {
        return typeMapper.getTypeById(id);
    }

    @Override
    public int addType(Type type) {
        return typeMapper.addType(type);
    }

    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public int deleteType(String id) {
        return typeMapper.deleteType(id);
    }

    @Override
    public List<Type> getAllTypeByParentTypeId(int parentTypeId) {
        return typeMapper.getAllTypeByParentTypeId(parentTypeId);
    }

    @Override
    public List<Type> getTypeList(int pageNo, int pageSize, Map<String, String> params) {
        int begin = (pageNo-1) * pageSize;
        return typeMapper.getTypeList(begin, pageSize, params);
    }

    @Override
    public int getCount(Map<String, String> params) {
        return typeMapper.getCount(params);
    }
}
