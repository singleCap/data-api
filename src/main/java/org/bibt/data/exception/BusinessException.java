package org.bibt.data.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bibt.data.enums.BusinessErrorEnum;

/**
 * 业务异常
 *
 * @author ZengFanyong
 * @date 2021/1/23
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {
    private int code;
    private String message;

    public BusinessException(BusinessErrorEnum businessErrorEnum) {
        this.code = businessErrorEnum.getCode();
        this.message = businessErrorEnum.getMsg();
    }

    public BusinessException(int code, String message) {
        this.code = code;
        this.message = message;
    }
}