package org.bibt.data.service;

import org.bibt.data.entity.User;

/**
 * 用户服务接口
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
public interface UserService {

    /**
     * 通过用户名称查询用户信息
     *
     * @param userName  用户名称
     * @return User     用户信息
     */
    User selectByName(String userName);

    /**
     * 通过用户编号查询用户信息
     *
     * @param userId    用户名称
     * @return User     用户信息
     */
    User selectById(String userId);

    /**
     * 用户登录
     *
     * @param userId    用户编号
     * @param passWord  用户密码
     * @return String   token凭证
     */
    String login(String userId, String passWord);

    /**
     * 注册用户
     *
     * @param userId    用户编号
     * @param userName  用户名称
     * @param password  用户密码
     * @param remark    用户备注
     */
    void register(String userId, String userName, String password, String remark);

    /**
     * 新增用户
     *
     * @param user  用户信息
     */
    void insetUser(User user);

    /**
     * 发送验证码
     *
     * @param userId    用户编号
     */
    void sendVerificationCode(String userId);

    /**
     * 使用验证码登录
     *
     * @param userId    用户编号
     * @param code      验证码
     * @return String   登录成功后的用户token
     */
    String verificationCodeLogin(String userId, String code);

}
