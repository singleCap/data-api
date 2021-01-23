package org.bibt.data.enums;

/**
 * 登录方式类型
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
public enum LoginEnum {

    /**
     * 密码登录
     */
    BY_PASSWORD("Password"),
    /**
     * 验证码登录
     */
    BY_CODE("Code");

    /**
     * 登录方式类型
     */
    private String loginType;

    LoginEnum(String loginType) {
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

}
