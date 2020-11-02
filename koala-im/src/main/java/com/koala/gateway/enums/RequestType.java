package com.koala.gateway.enums;

import java.util.Objects;

/**
 * @author XiuYang
 * @date 2019/10/16
 */

public enum RequestType {

    CONNECTION("CONNECTION","连接"),
    HEART_BEAT("HEART_BEAT","心跳"),
    CHAT_MSG_TEXT("CHAT_MSG_TEXT","发送文本消息"),
    CHAT_MSG_ACK("CHAT_MSG_ACK","消息ACK"),

    /**
     * http request
     */
    LOGIN("/login","登陆");

    RequestType(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public static RequestType getEnum(String code){
        for(RequestType requestType : RequestType.values()){
            if(Objects.equals(requestType.getCode() ,code)){
                return requestType;
            }
        }
        return null;
    }

    private String code;

    private String description;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
