package com.koala.gateway.tcp;

import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@Data
public class HeartBeatRequest extends BaseRequest {

    private final static ServerHandler<? extends BaseRequest> serverHandler = ProtocolFactory.instance
        .getServerHandler(EnumProtocolType.HEART_BEAT);

    public HeartBeatRequest(long requestId,int timeout){
        super(EnumProtocolType.HEART_BEAT.getCode(),requestId,timeout);
    }

    @Override
    public void encode(ByteBufferWrapper bytebufferWrapper) throws Exception {
        long id = this.getRequestId();
        bytebufferWrapper.ensureCapacity(HeartBeatProtocol.HEADER_LENGTH);
        bytebufferWrapper.writeByte(EnumProtocolType.HEART_BEAT.getCode());
        bytebufferWrapper.writeByte(HeartBeatProtocol.REQUEST);
        bytebufferWrapper.writeByte(HeartBeatProtocol.VERSION);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeByte((byte) 0);
        bytebufferWrapper.writeLong(id);
    }

    @Override
    public ServerHandler<? extends BaseRequest> getServerHandler() {
        return serverHandler;
    }
}
