package com.koala.gateway.dto;

import com.koala.gateway.enums.EnumProtocolType;
import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@Data
public class HeartBeatRequest extends BaseRequest {

    public HeartBeatRequest(long requestId,int timeout){
        super(EnumProtocolType.HEART_BEAT.getCode(),requestId,timeout);
    }

    public static void main(String[] args) {
        EnumProtocolType protocolType = EnumProtocolType.HEART_BEAT;

        System.out.println(protocolType.getCode());
    }
}
