package org.bibt.data.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 返回结果
 *
 * @author ZengFanyong
 * @date 2021/1/21
 */
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result implements Serializable {
    /** 序列号 */
    private static final long serialVersionUID = 1L;
    /** 返回编码 取值0（成功）和1（失败） */
    private int code;
    /** 返回信息 */
    private String message;
    /** 返回数据 */
    private Object data;

    /**
     * 请求成功
     *
     * @param data
     *      返回数据
     * @return Result
     *      返回成功结果
     */
    public static Result ok(Object data) {
        return new Result(0, "ok", data);
    }

    /**
     * 请求失败
     *
     * @return Result
     *      返回失败结果
     */
    public static Result error() {
        return new Result(1, "failure", null);
    }
}
