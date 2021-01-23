package org.bibt.data.base.shiro.token;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * 订制Token登录信息
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString
public class CustomizedToken extends UsernamePasswordToken {

    /**
     * 登录类型
     */
    public String loginType;

    public CustomizedToken(final String username, final String password, final String loginType) {
        super(username, password);
        this.loginType = loginType;
    }
}
