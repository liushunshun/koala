package com.koala.api.dto;

import com.koala.api.enums.ResponseStatus;
import lombok.Builder;
import lombok.Data;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Data
public class Result<T> {

    protected boolean ok = true;

    protected Integer errorCode = ResponseStatus.OK.getCode();

    protected String errorMessage = ResponseStatus.OK.getDescription();

    protected T data;

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 协议类型，心跳、发送聊天消息、数据请求、消息推送
     */
    private String requestType;

    public Result() {
    }

    public Result(ResponseStatus status) {
        if (ResponseStatus.OK != ResponseStatus.getEnum(status.getCode())) {
            setOk(false);
        }
        setErrorCode(status.getCode());
        setErrorMessage(status.getDescription());
    }

    public Result(ResponseStatus status, String msgExt) {
        if (ResponseStatus.OK != ResponseStatus.getEnum(status.getCode())) {
            setOk(false);
        }
        setErrorCode(status.getCode());
        setErrorMessage(status.getDescription() + ":" + msgExt);
    }

    public Result(Integer errorCode, String errorMsg) {
        if (ResponseStatus.OK != ResponseStatus.getEnum(errorCode)) {
            setOk(false);
        }
        setErrorCode(errorCode);
        setErrorMessage(errorMsg);
    }

    public static Result create(){
        return new Result();
    }

    public static <T> Result success(T data){
        Result result = new Result();
        result.setData(data);
        return result;
    }

    public Result error(ResponseStatus status){
        this.ok = false;
        this.errorCode = status.getCode();
        this.errorMessage = status.getDescription();
        return this;
    }

    public Result error(ResponseStatus status,String extMessage){
        this.ok = false;
        this.errorCode = status.getCode();
        this.errorMessage = status.getDescription() + ":"+extMessage;
        return this;
    }

    public Result response(ResponseStatus status){
        if(status != ResponseStatus.OK){
            this.ok = false;
        }
        this.errorCode = status.getCode();
        this.errorMessage = status.getDescription();
        return this;
    }

    public Result response(ResponseStatus status,String extMessage){
        if(status != ResponseStatus.OK){
            this.ok = false;
        }
        this.errorCode = status.getCode();
        this.errorMessage = status.getDescription() + ":"+extMessage;
        return this;
    }

    public Result response(Integer errorCode,String extMessage){
        if(ResponseStatus.getEnum(errorCode) != ResponseStatus.OK){
            this.ok = false;
        }
        this.errorCode = errorCode;
        this.errorMessage = extMessage;
        return this;
    }

}
