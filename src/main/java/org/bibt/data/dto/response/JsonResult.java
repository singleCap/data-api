package org.bibt.data.dto.response;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author ZengFanyong
 * @date 2021/1/21
 */
@Slf4j
@Data
public class JsonResult implements Serializable {
    /**
     * 序列号
     */
    private static final long serialVersionUID = 1L;
    /**
     * 返回编码 取值0（成功）和1（失败）
     */
    private int code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    private JsonResult(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 请求成功
     *
     * @param data 返回数据
     * @return Result
     * 返回成功结果
     */
    public static JsonResult ok(Object data) {
        return new JsonResult(0, "ok", data);
    }

    /**
     * 请求成功
     *
     * @return Result
     * 返回成功结果
     */
    public static JsonResult ok() {
        return new JsonResult(0, "ok", null);
    }

    /**
     * 请求失败
     *
     * @return Result
     * 返回失败结果
     */
    public static JsonResult error() {
        return new JsonResult(1, "failure", null);
    }

    /**
     * 请求失败
     *
     * @param message 消息
     * @return Result
     * 返回失败结果
     */
    public static JsonResult error(String message) {
        return new JsonResult(1, message, null);
    }
}
