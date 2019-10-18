package com.koala.gateway.tcp;

import com.koala.gateway.enums.EnumResponseStatus;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public abstract class BaseResponse extends BaseHeader{

    private EnumResponseStatus status = EnumResponseStatus.OK;

    public BaseResponse(byte protocolType, long requestId) {
        super(protocolType, requestId);
    }

    public EnumResponseStatus getStatus() {
        return status;
    }

    public void setStatus(EnumResponseStatus status) {
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = EnumResponseStatus.getEnum(status);
    }

    @Override
    public String toString() {
        return this.getProtocolType() + ":" + status;
    }

}
