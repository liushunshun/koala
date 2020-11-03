package com.koala.api.enums;

import java.util.Objects;

/**
 * @author XiuYang
 * @date 2020/10/26
 */
public enum SessionType {

    SINGLE(0,"单聊"),
    GROUP(1,"群聊");

    private Integer code;

    private String description;

    SessionType(Integer code,String description){
        this.code = code;
        this.description = description;
    }

    public static SessionType getEnum(Integer code){
        if(code == null){
            return null;
        }
        for(SessionType type : SessionType.values()){
            if(Objects.equals(type.getCode(),code)){
                return type;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
