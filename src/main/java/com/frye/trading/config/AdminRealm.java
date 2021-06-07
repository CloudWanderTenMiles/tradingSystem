package com.frye.trading.config;

import com.frye.trading.pojo.model.Admin;
import com.frye.trading.service.AdminService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminRealm extends AuthorizingRealm {

    @Autowired
    AdminService adminService;

    /**
     * 授权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        UserToken token = (UserToken) authenticationToken;
        String account = token.getUsername();
        Admin admin = adminService.getAdminByID(account);
        if (admin == null) {
            return null;
        }
        // 盐值加密
        ByteSource credentialsSalt = ByteSource.Util.bytes(admin.getAdminId());
        return new SimpleAuthenticationInfo(account, admin.getAdminPwd(), credentialsSalt, this.getName());
    }

    public static void main(String[] args) {
        String username = "1120";
        String credentials = "123456";
        String result = getEncryptedPassword(username, credentials);
        System.out.println(result);
    }

    public static String getEncryptedPassword(String username, String credentials) {
        String hashAlgorithmName = "MD5";
        Object salt = ByteSource.Util.bytes(username);
        int hashIterations = 1024;
        Object result = new SimpleHash(hashAlgorithmName, credentials, salt, hashIterations);
        return result.toString();
    }
}
