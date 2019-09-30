package com.koala.gateway.dto;

import com.koala.gateway.constants.GatewayConstants;
import com.koala.utils.UUIDGenerator;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public abstract class BaseRequest extends BaseHeader{

    private final int timeout;

    public BaseRequest(byte protocolType, long id, int timeout) {
        super(protocolType, id);
        this.timeout = timeout;
    }

    public BaseRequest(byte protocolType, int timeout) {
        this(protocolType, UUIDGenerator.getNextOpaque(), timeout);
    }

    public BaseRequest(byte protocolType) {
        this(protocolType, GatewayConstants.DEFAULT_TIMEOUT);
    }

    public int getTimeout() {
        return timeout;
    }

}
