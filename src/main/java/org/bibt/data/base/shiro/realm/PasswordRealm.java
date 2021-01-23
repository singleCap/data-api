package org.bibt.data.base.shiro.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.bibt.data.base.shiro.token.CustomizedToken;
import org.bibt.data.entity.User;
import org.bibt.data.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 密码域
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@Slf4j
public class PasswordRealm extends AuthorizingRealm {

    /**
     * 用户映射
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 是否支持
     *
     * @param token 凭证
     * @return 是否支持
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof CustomizedToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("PasswordRealm权限认证开始,传递的token:{}",authenticationToken);
        //找出数据库中的对象  给定用户输入的对象做出对比
        CustomizedToken token = (CustomizedToken) authenticationToken;
        log.info("PasswordRealm转换的自定义token是:{}",token);
        // 根据userId查询用户
        User user=userMapper.selectById(token.getUsername());
        if (user == null) {
            // 抛出账号不存在异常
            throw new UnknownAccountException();
        }
        Object credentials = user.getPassword();
        //param1:数据库用户 param2:密码 param3:加密所用盐值 param4:当前realm的名称
        return new SimpleAuthenticationInfo(user, credentials, ByteSource.Util.bytes(user.getUserId()),getName());
    }
}