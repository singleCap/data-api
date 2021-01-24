package org.bibt.data.exception;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authz.AuthorizationException;
import org.bibt.data.enums.BusinessErrorEnum;
import org.bibt.data.dto.response.JsonResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 全局异常处理器
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 拦截业务异常，返回业务异常信息
     *
     * @param exception 异常
     * @return Result   Json数据
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public JsonResult handleBusinessError(BusinessException exception) {
        int code = exception.getCode();
        String message = exception.getMessage();
        return JsonResult.error(code + ":" + message);
    }

    @ExceptionHandler
    @ResponseBody
    public JsonResult errorHandler(AuthorizationException e) {
        log.error("权限校验失败！", e);
        return JsonResult.error(BusinessErrorEnum.NO_AUTH.getCode() + ":" + BusinessErrorEnum.NO_AUTH.getMsg());
    }


    @ExceptionHandler
    @ResponseBody
    public JsonResult errorHandler(AuthenticationException e) {
        log.error("用户名或密码错误,用户登录失败！", e);
        return JsonResult.error(BusinessErrorEnum.ERROR_ACCOUNT.getCode() + ":" + BusinessErrorEnum.ERROR_ACCOUNT.getMsg());
    }

    @ExceptionHandler
    @ResponseBody
    public JsonResult errorHandler(IncorrectCredentialsException e) {
        log.error("用户名或密码错误,用户登录失败！", e);
        return JsonResult.error(BusinessErrorEnum.ERROR_ACCOUNT.getCode() + ":" + BusinessErrorEnum.ERROR_ACCOUNT.getMsg());
    }

}
