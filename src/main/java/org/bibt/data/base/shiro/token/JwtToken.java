package org.bibt.data.base.shiro.token;

import lombok.AllArgsConstructor;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * Jwt Token登录信息
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@AllArgsConstructor
public class JwtToken implements AuthenticationToken {

    /**
     * 认证凭证token
     */
    private String token;

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
