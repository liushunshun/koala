package com.koala.gateway.dto;

import com.koala.gateway.encoder.ByteBufferWrapper;
import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@Data
public abstract class BaseHeader {
    /**
     * 协议类型
     */
    private final byte protocolType;
    /**
     * 请求ID
     */
    private final long requestId;


    public BaseHeader(byte protocolType, long requestId) {
        this.protocolType = protocolType;
        this.requestId = requestId;
    }

    public abstract void encode(ByteBufferWrapper byteBuffer) throws Exception;
}
