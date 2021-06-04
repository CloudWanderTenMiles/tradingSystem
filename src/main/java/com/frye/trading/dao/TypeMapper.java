package com.frye.trading.dao;

import com.frye.trading.pojo.model.Type;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface TypeMapper {

    public Type getTypeById(@Param("id") String id);

    public int addType(Type type);

    public int updateType(Type type);

    public int deleteType(String id);

    public List<Type> getAllTypeByParentTypeId(@Param("parentTypeId") int parentTypeId);

    public List<Type> getTypeList(int begin, int pageSize, @Param("params") Map<String, String> params);

    public int getCount(@Param("params") Map<String, String> params);
}
