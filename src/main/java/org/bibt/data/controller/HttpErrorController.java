package org.bibt.data.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.bibt.data.dto.response.JsonResult;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 全局异常处理控制器
 *
 * @author ZengFanyong
 * @date 2021/1/22
 */
@Slf4j
@RestController
@Api(tags = "全局统一异常")
public class HttpErrorController implements ErrorController {

    private final static String ERROR_PATH = "/error";

    @ResponseBody
    @RequestMapping(path = ERROR_PATH)
    public JsonResult error(HttpServletRequest request, HttpServletResponse response) {
        String message = "response status is " + response.getStatus();
        return JsonResult.error(message);
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
