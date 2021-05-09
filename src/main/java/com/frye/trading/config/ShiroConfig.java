package com.frye.trading.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 配置AdminShiro
 */

@Configuration
public class ShiroConfig {

    @Bean(name = "shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("defaultWebSecurityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        bean.setSecurityManager(defaultWebSecurityManager);

        /** 自定义配置内置过滤器
         * anon: 无需认证就可以访问
         * authc: 必须认证才能访问
         * user: 必须拥有 “remember me”功能才能访问
         * perms: 必须拥有对某个资源的权限才能访问
         * role: 必须拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>();

        // 放开权限
        filterMap.put("/static/**", "anon");
        filterMap.put("/admin", "anon");
        filterMap.put("/admin/login", "anon");
        filterMap.put("/", "anon");
        filterMap.put("/customer/login", "anon");
        filterMap.put("/toPage/**", "anon");
        filterMap.put("/op/commodityList", "anon");
        filterMap.put("/op/customerAdd", "anon");

        filterMap.put("error", "anon");
        filterMap.put("/logout", "logout");

        // 认证后才能进入
        filterMap.put("toPage/admin/**", "authc");
        filterMap.put("/admin/**", "authc");
        filterMap.put("/op/**", "authc");

        // 配置未认证时的跳转页面
        bean.setLoginUrl("/admin");
        // 配置无权限时的跳转页面
        bean.setUnauthorizedUrl("/admin");
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }

    @Bean(name = "defaultWebSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
        List<Realm> realms = new ArrayList<>();
        realms.add(adminRealm(matcher));
        realms.add(customerRealm(matcher));
        realms.add(cstaffRealm(matcher));
        securityManager.setAuthenticator(modularRealmAuthenticator()); // 需要再realm定义之前
        securityManager.setRealms(realms);
        return securityManager;
    }

    @Bean(name = "adminRealm")
    public AdminRealm adminRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        AdminRealm adminRealm = new AdminRealm();
        adminRealm.setCredentialsMatcher(matcher);
        return adminRealm;
    }

    @Bean(name = "customerRealm")
    public CustomerRealm customerRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        CustomerRealm customerRealm = new CustomerRealm();
        customerRealm.setCredentialsMatcher(matcher);
        return customerRealm;
    }

    @Bean(name = "cstaffRealm")
    public CstaffRealm cstaffRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        CstaffRealm cstaffRealm = new CstaffRealm();
        cstaffRealm.setCredentialsMatcher(matcher);
        return cstaffRealm;
    }

    @Bean("hashedCredentialsMatcher")
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //指定加密方式为MD5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //加密次数
        credentialsMatcher.setHashIterations(1024);
        return credentialsMatcher;
    }

    /**
     * 系统自带的Realm管理，主要针对多realm 认证
     */
    @Bean
    public ModularRealmAuthenticator modularRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        ShiroRealmAuthenticator modularRealmAuthenticator = new ShiroRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }
}
