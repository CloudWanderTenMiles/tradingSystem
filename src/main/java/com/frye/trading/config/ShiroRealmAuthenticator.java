package com.frye.trading.config;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;

import java.util.ArrayList;
import java.util.Collection;

public class ShiroRealmAuthenticator extends ModularRealmAuthenticator {

    /**
     * 根据用户类型判断使用哪个Realm
     */
    @Override
    protected AuthenticationInfo doAuthenticate(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        super.assertRealmsConfigured();
        // 强制转换回自定义的CustomizedToken
        UserToken token = (UserToken) authenticationToken;
        // 登录类型
        String userType = token.getUserType();
        // 所有Realm
        Collection<Realm> realms = getRealms();
        // 登录类型对应的所有Realm
        Collection<Realm> typeRealms = new ArrayList<>();
        for (Realm realm : realms) {
            //根据登录类型和Realm的名称进行匹配区分
            if(realm.getName().contains(userType)){
                typeRealms.add(realm);
            }
        }

        // 判断是单Realm还是多Realm，有多个Realm就会使用所有配置的Realm。 只有一个的时候，就直接使用当前的Realm。
        if (typeRealms.size() == 1) {
            return doSingleRealmAuthentication(typeRealms.iterator().next(), token);
        } else {
            return doMultiRealmAuthentication(typeRealms, token);
        }
    }

}
