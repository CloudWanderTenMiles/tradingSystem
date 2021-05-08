package com.frye.trading.dao;

import com.frye.trading.pojo.model.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomerMapper {

    public int checkExist(@Param("params") Map<String, String> params);

    public Customer getCustomerById(@Param("id") String id);

    public Customer getCustomerByPhone(@Param("phone") String phone);

    public int changePwd(String id, String pwd);

    public int addCustomer(Customer customer);

    public int updateCustomer(Customer customer);

    public int deleteCustomer(String id);

    public List<Customer> getCustomerList(int begin, int pageSize, @Param("params") Map<String, String> params);

    public int getCount(@Param("params") Map<String, String> params);
}
