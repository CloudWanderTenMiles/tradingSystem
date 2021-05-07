package com.frye.trading.config;

import com.frye.trading.pojo.model.Admin;
import com.frye.trading.pojo.model.Customer;
import com.frye.trading.service.CustomerService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomerRealm extends AuthorizingRealm {

    @Autowired
    CustomerService customerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//        String phone = token.getUsername();
//        Customer customer = customerService.getCustomerById(account);
//        if (customer == null) {
//            return null;
//        }
//        // 盐值加密
//        ByteSource credentialsSalt = ByteSource.Util.bytes(customer.getAdminName());
//        return new SimpleAuthenticationInfo(account, admin.getAdminPwd(), credentialsSalt, this.getName());
        return null;
    }

    public static String getEncryptedPassword(String username, String credentials) {
        String hashAlgorithmName = "MD5";
        Object salt = ByteSource.Util.bytes(username);
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        return result.toString();
    }
}
