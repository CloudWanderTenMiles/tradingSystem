package com.frye.trading.service.Impl;

import com.frye.trading.dao.CustomerMapper;
import com.frye.trading.pojo.model.Customer;
import com.frye.trading.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Override
    public boolean checkExist(String stuId, String phone, String email) {
        Map<String, String> params = new LinkedHashMap<>();
        params.put("studentId", stuId);
        params.put("phone", phone);
        params.put("email", email);
        return customerMapper.checkExist(params) > 0;
    }

    @Override
    public Customer getCustomerById(String id) {
        return customerMapper.getCustomerById(id);
    }

    @Override
    public int changePwd(String id, String pwd) {
        return customerMapper.changePwd(id, pwd);
    }

    @Override
    public int addCustomer(Customer customer) {
        return customerMapper.addCustomer(customer);
    }

    @Override
    public int updateCustomer(Customer customer) {
        return customerMapper.updateCustomer(customer);
    }

    @Override
    public int deleteCustomer(String id) {
        return customerMapper.deleteCustomer(id);
    }

    @Override
    public List<Customer> getCustomerList(int pageNo, int pageSize, Map<String, String> params) {
        int begin = (pageNo - 1) * pageSize;
        return customerMapper.getCustomerList(begin, pageSize, params);
    }

    @Override
    public int getCount(Map<String, String> params) {
        return customerMapper.getCount(params);
    }
}
