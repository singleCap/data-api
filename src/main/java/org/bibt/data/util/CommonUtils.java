package org.bibt.data.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import javax.servlet.http.HttpServletRequest;

/**
 * 通用工具
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
public class CommonUtils {

    /**
     * 手机号正则校验
     *
     * @param phone 手机号
     * @return 校验是否成功
     */
    public static boolean phoneRegexCheck(String phone){
        return phone.length() == Constant.CHECK_LENGTH_PHONE_NUMBER;
    }

    /**
     * 获取六位数验证码
     *
     * @return 验证码
     */
    public static int getCode(){
        return (int)((Math.random()*9+1)*100000);
    }

    /**
     * 使用md5加密
     *
     * @param password 需要加密的密码
     * @param phoneNumber 手机号
     * @return 返回加密后的密码
     */
    public static String encryptPassword(String password, String phoneNumber){
        //userId作为盐值
        return String.valueOf(new SimpleHash("MD5", password, phoneNumber, 1024));
    }

    /**
     * 获取请求域中的UserId
     *
     * @return 返回用户编号
     */
    public static Integer getUserId(HttpServletRequest request){
        return Integer.parseInt(request.getAttribute(Constant.USER_ID).toString());
    }

}
