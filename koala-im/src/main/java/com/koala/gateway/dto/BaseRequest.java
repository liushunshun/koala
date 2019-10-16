package com.koala.gateway.dto;

import com.koala.gateway.constants.GatewayConstants;
import com.koala.gateway.handler.ServerHandler;
import com.koala.utils.UUIDGenerator;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public abstract class BaseRequest<T> extends BaseHeader{

    private T data;

    public BaseRequest(byte protocolType, long id, T data) {
        super(protocolType, id);
        this.data = data;
    }

    public BaseRequest(byte protocolType, T data) {
        this(protocolType, UUIDGenerator.getNextOpaque(), data);
    }

    public T getData() {
        return data;
    }

    public abstract ServerHandler<? extends BaseRequest> getServerHandler();

}
