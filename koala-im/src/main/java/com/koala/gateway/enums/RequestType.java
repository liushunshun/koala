package com.koala.gateway.enums;

import com.koala.gateway.dto.KoalaAckRequest;
import com.koala.gateway.dto.KoalaSendRequest;
import com.koala.gateway.dto.KoalaRequest;

import java.util.Objects;

/**
 * @author XiuYang
 * @date 2019/10/16
 */

public enum RequestType {

    CONNECTION("CONNECTION","连接", KoalaRequest.class),
    HEART_BEAT("HEART_BEAT","心跳", KoalaRequest.class),
    CHAT_MSG_SEND("CHAT_MSG_SEND","发送聊天消息", KoalaSendRequest.class),
    CHAT_MSG_ACK("CHAT_MSG_ACK","消息ACK",KoalaAckRequest.class);

    RequestType(String code, String description, Class dtoClazz) {
        this.code = code;
        this.description = description;
        this.dtoClazz = dtoClazz;
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
