package com.koala.gateway.connection;

import com.koala.gateway.dto.BaseResponse;

import java.net.SocketAddress;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public interface Connection {

    SocketAddress getLocalAddress();

    SocketAddress getRemoteAddress();

    String getPeerIP();

    void refreshLastReadTime(long lastReadTime);

    long getLastReadTime();

    void writeResponse(BaseResponse responseWrapper);

    void close();

}

