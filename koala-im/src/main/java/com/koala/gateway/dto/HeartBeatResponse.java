package com.koala.gateway.dto;

import com.koala.gateway.enums.EnumProtocolType;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class HeartBeatResponse extends BaseResponse{
    public HeartBeatResponse(long id) {
        super(EnumProtocolType.HEART_BEAT.getCode(), id);
    }
}
