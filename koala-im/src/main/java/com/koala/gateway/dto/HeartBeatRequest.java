package com.koala.gateway.dto;

import com.koala.gateway.encoder.ByteBufferWrapper;
import com.koala.gateway.enums.EnumProtocolType;
import com.koala.gateway.protocol.HeartBeatProtocol;
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

    @Override
    public void encode(ByteBufferWrapper bytebufferWrapper) throws Exception {
        long id = this.getRequestId();
        int timeout = this.getTimeout();
        bytebufferWrapper.ensureCapacity(HeartBeatProtocol.HEADER_LENGTH);
        bytebufferWrapper.writeByte(EnumProtocolType.HEART_BEAT.getCode());
        bytebufferWrapper.writeByte(HeartBeatProtocol.REQUEST);
        bytebufferWrapper.writeByte(HeartBeatProtocol.VERSION);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeLong(id);
        bytebufferWrapper.writeInt(timeout);
    }
}
