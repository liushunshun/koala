package com.koala.gateway.tcp;

import com.koala.gateway.tcp.BaseResponse;

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

