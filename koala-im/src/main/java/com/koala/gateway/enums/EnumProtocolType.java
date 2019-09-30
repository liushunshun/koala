package com.koala.gateway.enums;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public enum EnumProtocolType {

    HEART_BEAT((byte) 1,"心跳");

    EnumProtocolType(byte code, String description) {
        this.code = code;
        this.description = description;
    }


    public static EnumProtocolType getEnum(byte code){
        for(EnumProtocolType protocolType : EnumProtocolType.values()){
            if(protocolType.getCode() == code){
                return protocolType;
            }
        }
        return null;
    }

    private byte code;

    private String description;

    public byte getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

}
