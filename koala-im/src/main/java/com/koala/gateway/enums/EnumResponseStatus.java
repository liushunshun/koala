package com.koala.gateway.enums;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public enum  EnumResponseStatus {
    OK(0,"成功"),
    INVALID_PARAM(1,"非法参数"),
    SYSTEM_EXCEPTION(2,"非法参数");

    EnumResponseStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }


    public static EnumResponseStatus getEnum(int code){
        for(EnumResponseStatus status : EnumResponseStatus.values()){
            if(status.getCode() == code){
                return status;
            }
        }
        return null;
    }

    private int code;

    private String description;

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
