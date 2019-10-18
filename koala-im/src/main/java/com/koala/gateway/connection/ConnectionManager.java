package com.koala.gateway.connection;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Component
public class ConnectionManager {

    public static final Map<String,Channel> channels = new ConcurrentHashMap<>();

    public void connect(ConnectionParam connectionParam, Channel channel){
        channels.put(connectionParam.uniqueKey(),channel);
    }

    public void close(ConnectionParam connectionParam){
        channels.remove(connectionParam.uniqueKey());
    }
}
