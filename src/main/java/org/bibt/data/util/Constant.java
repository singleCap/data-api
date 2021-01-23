package org.bibt.data.util;

/**
 * 常量
 *
 * @author ZengFanyong
 * @date 2021/1/22
 */
public class Constant {
    /**
     * 请求头中的token名称
     */
    public final static String TOKEN_HEADER_NAME = "X-Access-Token";
    /**
     * token签名
     */
    public static final String TOKEN_SECRET = "bibt";
    /**
     * token的载荷中盛放的信息 只盛放一个userId 其余什么也不再盛放
     */
    public static final String TOKEN_CLAIM="userId";
    /**
     * UserId
     */
    public static final String USER_ID = "userId";
    /**
     * jwtToken过期时间 默认为30天
     * public static Integer TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000;
     */
    public static final Long TOKEN_EXPIRE_TIME = 31 * 24 * 60 * 60 * 1000L;
    /**
     * 验证码过期时间 此处为五分钟
     */
    public static final Integer CODE_EXPIRE_TIME = 60 * 5;
    /**
     * redis存放用户验证码时给的前缀
     */
    public static final String REDIS_LOGIN_CODE="LOGIN_CODE:";


    /**
     * 校验手机号码长度
     */
    public static final int CHECK_LENGTH_PHONE_NUMBER = 11;
}
