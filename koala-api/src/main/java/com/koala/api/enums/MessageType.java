package com.koala.api.enums;

import java.util.Objects;

/**
 * @author XiuYang
 * @date 2020/10/26
 */
public enum MessageType {

    TEXT("text","文本");

    private String code;

    private String description;

    MessageType(String code, String description){
        this.code = code;
        this.description = description;
    }

    public static MessageType getEnum(String code){
        if(code == null){
            return null;
        }
        for(MessageType type : MessageType.values()){
            if(Objects.equals(type.getCode(),code)){
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
