package com.frye.trading.service;

import com.frye.trading.pojo.model.Customer;

import java.util.List;
import java.util.Map;

public interface CustomerService {

    public boolean checkExist(String stuId, String phone, String email, String name);

    public Customer getCustomerById(String id);

    public String getCustomerPhotoByName(String name);

    public Customer getCustomerByPhone(String phone);

    public int changePwd(String id, String pwd);

    public int addCustomer(Customer customer);

    public int updateCustomer(Customer customer);

    public int deleteCustomer(String id);

    public List<Customer> getCustomerList(int pageNo, int pageSize, Map<String, String> params);

    public int getCount(Map<String, String> params);
}
