package com.frye.trading.dao;

import com.frye.trading.pojo.model.ParentType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ParentTypeMapper {

    public List<ParentType> getParentTypeList(@Param("begin") int begin,@Param("pageSize") int pageSize,
                                              @Param("parentTypeName") String parentTypeName);

    public List<ParentType> getAllParentType();

    public int getCount(@Param("parentTypeName") String parentTypeName);
}
