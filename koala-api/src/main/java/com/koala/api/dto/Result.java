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

    public Result() {
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
