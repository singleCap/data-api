package org.bibt.data.dto.request;


import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 验证码登录参数封装
 *
 * @author ZengFanyong
 * @date 2021/1/21
 */
@Data
public class CodeLoginDTO {

    /**
     * 手机号
     */
    @NotBlank(message = "用户验证码登录传递的手机号不可为空")
    @Size(max = 11,min = 11,message = "传递的验证码登录手机号必须为11位")
    private String userId;

    /**
     * 用户验证码
     */
    @NotNull(message = "用户验证码登录传递的验证码不可为空")
    @Size(max = 6,min = 6,message = "传递的验证码登录验证码必须为6位")
    private String code;
}
