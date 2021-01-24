package org.bibt.data.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AtLeastOneSuccessfulStrategy;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.bibt.data.base.shiro.filter.JwtFilter;
import org.bibt.data.base.shiro.realm.CodeRealm;
import org.bibt.data.base.shiro.realm.JwtRealm;
import org.bibt.data.base.shiro.realm.PasswordRealm;
import org.bibt.data.base.shiro.realm.UserModularRealmAuthenticator;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * Shiro配置
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@Configuration
public class ShiroConfig {

    /**
     * 认证匹配器
     *
     * @return HashedCredentialsMatcher
     */
     @Bean("hashedCredentialsMatcher")
     public HashedCredentialsMatcher hashedCredentialsMatcher(){
         HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
         // 设置哈希算法名称
         matcher.setHashAlgorithmName("MD5");
         // 设置哈希迭代次数
         matcher.setHashIterations(1024);
         // 设置存储凭证十六进制编码
         matcher.setStoredCredentialsHexEncoded(true);
         return matcher;
     }

    /**
     * 密码域
     *
     * @param matcher   认证匹配器
     * @return PasswordRealm 密码域
     */
    @Bean
    public PasswordRealm passwordRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher) {
        PasswordRealm passwordRealm = new PasswordRealm();
        passwordRealm.setCredentialsMatcher(matcher);
        return passwordRealm;
    }

    /**
     * 验证码域
     *
     * @param matcher   认证匹配器
     * @return CodeRealm 验证码域
     */
    @Bean
    public CodeRealm codeRealm(@Qualifier("hashedCredentialsMatcher") HashedCredentialsMatcher matcher){
        CodeRealm codeRealm=new CodeRealm();
        codeRealm.setCredentialsMatcher(matcher);
        return codeRealm;
    }

    /**
     * jwtRealm
     *
     * @return JwtRealm
     */
    @Bean
    public JwtRealm jwtRealm() {
        return new JwtRealm();
    }

    /**
     * Shiro内置过滤器，可以实现拦截器相关的拦截器
     * 常用的过滤器：
     * anon：无需认证（登录）可以访问
     * authc：必须认证才可以访问
     * user：如果使用rememberMe的功能可以直接访问
     * perms：该资源必须得到资源权限才可以访问
     * role：该资源必须得到角色权限才可以访问
     **/
    @Bean
    public ShiroFilterFactoryBean shiroFilter(@Qualifier("securityManager") SecurityManager securityManager) {
        ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置 SecurityManager
        bean.setSecurityManager(securityManager);
        // 设置未登录跳转url
        bean.setUnauthorizedUrl("/sys/v1/user/unLogin");
        Map<String, String> filterMap = new LinkedHashMap<>();
        filterMap.put("/sys/v1/user/passwordLogin", "anon");
        filterMap.put("/sys/v1/user/verificationCodeLogin", "anon");
        filterMap.put("/sys/v1/user/register", "anon");
        bean.setFilterChainDefinitionMap(filterMap);
        Map<String, Filter> filter = new HashMap<>(1);
        filter.put("jwt", new JwtFilter());
        bean.setFilters(filter);
        // 过滤链定义，从上向下顺序执行，一般将放在最为下边
        filterMap.put("/**", "jwt");
        bean.setFilterChainDefinitionMap(filterMap);
        return bean;
    }

    /**
     * 自定义多域验证器
     *
     * @return 自定义多域验证器
     */
    @Bean
    public UserModularRealmAuthenticator userModularRealmAuthenticator() {
        //自己重写的ModularRealmAuthenticator
        UserModularRealmAuthenticator modularRealmAuthenticator = new UserModularRealmAuthenticator();
        modularRealmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        return modularRealmAuthenticator;
    }

    /**
     * SecurityManager 是 Shiro 架构的核心，通过它来链接Realm和用户(文档中称之为Subject.)
     */
    @Bean
    public SecurityManager securityManager(
            @Qualifier("codeRealm") CodeRealm codeRealm,
            @Qualifier("passwordRealm") PasswordRealm passwordRealm,
            @Qualifier("jwtRealm") JwtRealm jwtRealm,
            @Qualifier("userModularRealmAuthenticator") UserModularRealmAuthenticator userModularRealmAuthenticator) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 设置realm
        securityManager.setAuthenticator(userModularRealmAuthenticator);
        List<Realm> realms = new ArrayList<>();
        // 添加多个realm
        realms.add(passwordRealm);
        realms.add(jwtRealm);
        realms.add(codeRealm);
        securityManager.setRealms(realms);

        /*
         * 关闭shiro自带的session，详情见文档
         */
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }


    /**
     * 以下Bean开启shiro权限注解
     *
     * @return DefaultAdvisorAutoProxyCreator
     */
    @Bean
    public static DefaultAdvisorAutoProxyCreator creator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }
}
