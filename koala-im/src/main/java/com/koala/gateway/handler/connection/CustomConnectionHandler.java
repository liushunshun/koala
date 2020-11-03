package com.koala.gateway.handler.connection;

import com.koala.gateway.connection.ConnectionParam;
import io.netty.channel.Channel;

/**
 * @author XiuYang
 * @date 2019/10/21
 */

public interface CustomConnectionHandler {

    void connect(ConnectionParam param, Channel channel);

    void close(ConnectionParam param);
}
