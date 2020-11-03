package com.koala.gateway.handler.connection;

import com.koala.gateway.connection.ConnectionManager;
import com.koala.gateway.connection.ConnectionParam;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2019/10/21
 */

@Component
public class UserConnectionManageHandler implements CustomConnectionHandler {

    @Autowired
    private ConnectionManager connectionManager;

    @Override
    public void connect(ConnectionParam param, Channel channel) {
        connectionManager.connect(param,channel);
    }

    @Override
    public void close(ConnectionParam param) {
        connectionManager.close(param);
    }
}
