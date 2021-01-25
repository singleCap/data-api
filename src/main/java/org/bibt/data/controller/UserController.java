package org.bibt.data.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.bibt.data.dto.request.CodeLoginDTO;
import org.bibt.data.dto.request.PassWordLoginDTO;
import org.bibt.data.dto.request.RegisterDTO;
import org.bibt.data.dto.response.JsonResult;
import org.bibt.data.enums.BusinessErrorEnum;
import org.bibt.data.exception.BusinessException;
import org.bibt.data.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

/**
 * 用户资源控制器
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@RestController
@RequestMapping(value = "/sys/v1")
@Api(tags = "用户")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户权限
     *
     * @return 用户权限
     * @throws Exception 异常
     */
    @RequiresRoles(value = {"admin", "common"}, logical = Logical.OR)
    @GetMapping(value = "/user/getUserPermission")
    public JsonResult getUserPermission() throws Exception {
        log.info("访问接口getUserPermission成功");
        return JsonResult.ok();
    }

    /**
     * 获取所有用户
     *
     * @return  所有用户
     * @throws Exception 异常
     */
    @RequiresRoles(value = {"admin"})
    @GetMapping(value = "/user/getUser")
    public JsonResult getUser() throws Exception {
        log.info("访问getUser接口成功");
        return JsonResult.ok();
    }

    /**
     * 用户使用密码登录
     *
     * @param passWordLoginDTO  用户密码登录信息
     * @return 用户登录后的token
     */
    @PostMapping(value = "/user/passwordLogin",name = "用户密码登录")
    public JsonResult passwordLogin(@RequestBody @Valid PassWordLoginDTO passWordLoginDTO) {
        log.info("传递的请求参数:{}",passWordLoginDTO);
        try {
            String token = userService.login(passWordLoginDTO.getUserId(), passWordLoginDTO.getPassword());
            return JsonResult.ok(token);
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            log.info("错误信息:{}", e.getMessage());
            if(e instanceof IncorrectCredentialsException){
                throw e;
            }
            throw new BusinessException(BusinessErrorEnum.SERVER_ERROR);
        }
    }

    /**
     * 用户注册
     * 此为shiro开放端口
     *
     * @param registerDTO   用户注册信息
     * @return 注册是否成功
     */
    @RequiresRoles(value = {"admin"})
    @PostMapping(value = "/user/register",name = "用户注册")
    public JsonResult userRegister(@RequestBody @Valid RegisterDTO registerDTO){
        try {
            userService.register(registerDTO.getUserId(), registerDTO.getUserName(), registerDTO.getPassword(),
                    registerDTO.getRemark());
            return JsonResult.ok();
        }catch (BusinessException e){
            throw e;
        } catch (Exception e){
            throw new BusinessException(BusinessErrorEnum.SERVER_ERROR);
        }
    }

    /**
     * 发送验证码
     * 此为shiro的开放端口 为用户发送登录验证码
     *
     * @param userId    用户编号
     * @return 发送验证码是否成功
     */
    @GetMapping(value = "/user/sendVerificationCode")
    public JsonResult sendVerificationCode(String userId){
        if(StringUtils.isEmpty(userId)) {
            throw new BusinessException(BusinessErrorEnum.BAD_PARAM);
        }
        try{
            userService.sendVerificationCode(userId);
            return JsonResult.ok();
        }catch (BusinessException e){
            //如果是我们的异常 直接扔出去
            throw e;
        }catch (Exception e){
            //如果不是我们期待的异常,返回服务器错误就好
            log.info("错误信息",e);
            throw new BusinessException(BusinessErrorEnum.SERVER_ERROR);
        }
    }

    /**
     * 用户使用验证码登录
     *
     * @param codeLoginDTO  验证码登录信息
     * @return 用户登录后的token
     */
    @PostMapping(value = "/user/verificationCodeLogin",name = "用户验证码登录")
    public JsonResult verificationCodeLogin(@RequestBody @Valid CodeLoginDTO codeLoginDTO){
        try {
            String token = userService.verificationCodeLogin(codeLoginDTO.getUserId(), codeLoginDTO.getCode());
            return JsonResult.ok(token);
        }catch (BusinessException e){
            throw e;
        }catch (Exception e){
            log.info("异常:{}", e.getMessage());
            throw new BusinessException(BusinessErrorEnum.SERVER_ERROR);
        }
    }
}
