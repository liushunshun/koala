package com.koala.api;

import com.koala.api.enums.ResponseStatus;
import lombok.Data;

import java.util.Map;

/**
 * @author XiuYang
 * @date 2020/10/23
 */

@Data
public class BizException extends Exception{

    private Integer code;

    private String message;

    private Map<String,Object> data;

    public BizException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.code = errorCode;
        this.message = errorMessage;
    }

    public BizException(ResponseStatus status) {
        super(status.getDescription());
        this.code = status.getCode();
        this.message = status.getDescription();
    }

    public BizException(ResponseStatus status, Map<String,Object> data) {
        super(status.getDescription());
        this.code = status.getCode();
        this.message = status.getDescription();
        this.data = data;
    }

    public BizException(ResponseStatus status, String extMessage) {
        super(status.getDescription());
        this.code = status.getCode();
        this.message = status.getDescription() + ":"+extMessage;
    }
}