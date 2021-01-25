package org.bibt.data.dto.request;

import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 注册用户参数封装
 *
 * @author ZengFanyong
 * @date 2021/1/21
 */
@Data
public class RegisterDTO {

    /**
     * 用户手机号
     */
    @NotNull(message = "用户输入的手机号不可为空")
    @Size(max = 11,min = 11,message = "手机号必须为11位")
    private String userId;

    /**
     * 用户密码
     */
    @NotBlank(message = "用户输入的注册密码不可为空")
    @Size(max = 8,min = 6,message = "用户输入的密码长度必须在6-8之间")
    private String password;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户备注
     */
    private String remark;
}
