package com.koala.gateway.server.connection;

import com.koala.gateway.server.connection.ConnectionParam;
import io.netty.channel.Channel;

/**
 * @author XiuYang
 * @date 2019/10/21
 */

public interface CustomConnectionHandler {

    void connect(ConnectionParam param, Channel channel);

    void close(ConnectionParam param);
}
