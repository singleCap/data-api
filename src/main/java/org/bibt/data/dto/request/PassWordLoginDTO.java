package org.bibt.data.dto.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 密码登录参数封装
 *
 * @author ZengFanyong
 * @date 2021/1/21
 */
@Data
@ToString
public class PassWordLoginDTO {

    /**
     * 用户编号
     */
    @NotNull(message = "用户密码登录传递的id不能为空")
    private String userId;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户密码登录传递的密码名称不能为空")
    private String password;
}
