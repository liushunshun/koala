package com.koala.api.enums;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public enum ResponseStatus {
    OK(0,"成功"),
    INVALID_PARAM(1,"非法参数"),
    SYSTEM_EXCEPTION(2,"系统异常"),
    AUTH_FAILED(3,"鉴权失败"),
    CONNECTION_NOT_ONLINE(4,"用户端未建立连接"),
    CONNECTION_NOT_ACTIVEE(5,"用户端连接不在活跃状态"),
    INVALID_MSG_TYPE(6,"不支持的消息类型");

    ResponseStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }


    public static ResponseStatus getEnum(int code){
        for(ResponseStatus status : ResponseStatus.values()){
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
