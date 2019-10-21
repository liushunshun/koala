package com.koala.core.common.dto;

import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/10/21
 */
@Data
public class Result<T> {

    private boolean ok = true;

    private Integer errorCode;

    private String errorMessage;

    private T data;

}
