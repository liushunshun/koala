package com.koala.gateway.enums;

import com.koala.gateway.dto.KoalaMessageAckRequest;
import com.koala.gateway.dto.KoalaMessageSendRequest;

import java.util.Objects;

/**
 * @author XiuYang
 * @date 2019/10/16
 */

public enum EnumRequestType {

    HEART_BEAT("HEART_BEAT","心跳", KoalaMessageSendRequest.class),
    CHAT_MSG_SEND("CHAT_MSG_SEND","发送聊天消息", KoalaMessageSendRequest.class),
    CHAT_MSG_ACK("CHAT_MSG_ACK","消息ACK",KoalaMessageAckRequest.class);

    EnumRequestType(String code, String description,Class dtoClazz) {
        this.code = code;
        this.description = description;
        this.dtoClazz = dtoClazz;
    }


    public static EnumRequestType getEnum(String code){
        for(EnumRequestType requestType : EnumRequestType.values()){
            if(Objects.equals(requestType.getCode() ,code)){
                return requestType;
            }
        }
        return null;
    }

    private String code;

    private String description;

    private Class dtoClazz;

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Class getDtoClazz(){
        return dtoClazz;
    }
}
