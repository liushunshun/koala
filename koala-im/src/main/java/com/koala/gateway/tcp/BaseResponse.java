package com.koala.gateway.tcp;

import com.koala.api.enums.ResponseStatus;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public abstract class BaseResponse extends BaseHeader{

    private ResponseStatus status = ResponseStatus.OK;

    public BaseResponse(byte protocolType, long requestId) {
        super(protocolType, requestId);
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public void setStatus(int status) {
        this.status = ResponseStatus.getEnum(status);
    }

    @Override
    public String toString() {
        return this.getProtocolType() + ":" + status;
    }

}
