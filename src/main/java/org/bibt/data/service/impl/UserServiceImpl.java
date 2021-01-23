package org.bibt.data.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.bibt.data.base.shiro.token.CustomizedToken;
import org.bibt.data.entity.User;
import org.bibt.data.enums.BusinessErrorEnum;
import org.bibt.data.enums.LoginEnum;
import org.bibt.data.exception.BusinessException;
import org.bibt.data.mapper.UserMapper;
import org.bibt.data.mapper.UserRoleMapper;
import org.bibt.data.service.UserService;
import org.bibt.data.util.CommonUtils;
import org.bibt.data.util.Constant;
import org.bibt.data.util.JwtUtil;
import org.bibt.data.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 用户服务接口实现
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    /**
     * 用户映射
     */
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户角色关系映射
     *
     */
    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * Redis工具
     */
    @Autowired
    private RedisUtil redisUtil;

    @Override
    public User selectByName(String userName) {
        return userMapper.selectByName(userName);
    }

    @Override
    public User selectById(String userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public String login(String userId, String passWord) {
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 校验userId是否为空
        if (StringUtils.isEmpty(userId)) {
            throw new BusinessException(BusinessErrorEnum.ACCOUNT_UNUSUAL);
        }
        // 校验数据库中此user是否存在
        User user = this.selectById(userId);
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.ACCOUNT_UNUSUAL);
        }
        // 制作CustomizedToken执行登录
        CustomizedToken customizedToken = new CustomizedToken(userId, passWord, LoginEnum.BY_PASSWORD.getLoginType());
        subject.login(customizedToken);
        // 若登陆成功返回相关token
        return JwtUtil.sign(user.getUserId(), Constant.TOKEN_SECRET);
    }

    @Override
    public void register(String userId, String userName, String password, String remark) {
        // 首先检查此用户是否在数据库
        if (this.selectById(userId) != null) {
            throw new BusinessException(500, "该手机号已经注册");
        }
        // 制作用户密码,然后将用户插入user表中
        String encryptPassword = CommonUtils.encryptPassword(password, String.valueOf(userId));
        log.info("加密之后的用户密码是:{}", encryptPassword);
        this.insetUser(User.getUser(userId, userName, encryptPassword, remark));
        // 增加用户角色中间表,注册最基本角色
        userRoleMapper.insert(userId, 200);
    }

    @Override
    public void insetUser(User user) {
        userMapper.insertUser(user);
    }

    @Override
    public void sendVerificationCode(String userId) {
        //根据userId查询这个用户
        if (StringUtils.isEmpty(this.selectById(userId))) {
            throw new BusinessException(BusinessErrorEnum.USER_NOT_EXISTS);
        }
        //获取六位随机数
        int code = CommonUtils.getCode();
        log.info("获取到的六位随机数是:{}", code);
        //将六位数加密
        String encryptPassword = CommonUtils.encryptPassword(String.valueOf(code), userId);
        log.info("本次加密的密码是:{}", encryptPassword);
        //存储redis
        redisUtil.set(Constant.REDIS_LOGIN_CODE + userId, encryptPassword, Constant.CODE_EXPIRE_TIME);
    }

    @Override
    public String verificationCodeLogin(String userId, String code) {
        // 获取Subject
        Subject subject = SecurityUtils.getSubject();
        // 校验数据库中此user是否存在
        User user = this.selectById(userId);
        if (user == null) {
            throw new BusinessException(BusinessErrorEnum.ACCOUNT_UNUSUAL);
        }
        // 制作CustomizedToken执行登录
        CustomizedToken customizedToken = new CustomizedToken(userId, code, LoginEnum.BY_CODE.getLoginType());
        subject.login(customizedToken);
        // 若登陆成功返回相关token
        String sign = JwtUtil.sign(user.getUserId(), Constant.TOKEN_SECRET);
        //删除redis中的验证码
        redisUtil.del(Constant.REDIS_LOGIN_CODE + userId);
        return sign;
    }
}
