package com.koala.gateway.dto;

import com.koala.gateway.encoder.ByteBufferWrapper;
import com.koala.gateway.enums.EnumProtocolType;
import com.koala.gateway.protocol.HeartBeatProtocol;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class HeartBeatResponse extends BaseResponse{
    public HeartBeatResponse(long id) {
        super(EnumProtocolType.HEART_BEAT.getCode(), id);
    }

    @Override
    public void encode(ByteBufferWrapper bytebufferWrapper) throws Exception {
        bytebufferWrapper.ensureCapacity(HeartBeatProtocol.HEADER_LENGTH);
        bytebufferWrapper.writeByte(EnumProtocolType.HEART_BEAT.getCode());
        bytebufferWrapper.writeByte(HeartBeatProtocol.RESPONSE);
        bytebufferWrapper.writeByte(HeartBeatProtocol.VERSION);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeLong(this.getRequestId());
        bytebufferWrapper.writeInt(0);
    }
}
