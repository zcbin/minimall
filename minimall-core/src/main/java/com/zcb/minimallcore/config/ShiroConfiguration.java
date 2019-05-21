package com.zcb.minimallcore.config;

import com.zcb.minimallcore.realm.MyRealm;

import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * shiro过滤配置
 */
@Configuration
public class ShiroConfiguration {
    //将自己的验证方式加入容器
    @Bean
    public MyRealm myShiroRealm() {
        MyRealm myRealm = new MyRealm();
        return myRealm;
    }

    //权限管理，配置主要是Realm的管理认证

    /**
     * 安全管理员
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
       // LOGGER.info("securityManager start");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
        return securityManager;
    }

    //Filter工厂，设置对应的过滤条件和跳转条件
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
       // LOGGER.info("config shiro filter");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String,String> map = new HashMap<String, String>();
        //登出
        map.put("/logout","logout");
        //微信登录 不验证
        map.put("/wx/auth/login_wx", "anon");
        //对所有用户认证
        map.put("/**","authc");
        //登录
        shiroFilterFactoryBean.setLoginUrl("/unauth"); //跳转至登录接口
        //首页
        shiroFilterFactoryBean.setSuccessUrl("/index");
        //错误页面，认证不通过跳转
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    //加入注解的使用，不加入这个注解不生效
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }
}
