package com.koala.gateway.connection;

import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Component
public class ConnectionManager {

    @Autowired
    private ConnectionChecker connectionChecker;

    private static final Map<String,Channel> channels = new ConcurrentHashMap<>();

    @PostConstruct
    private void startChecker(){
        connectionChecker.check();
    }


    public Map<String,Channel> getAllChannels(){
        return channels;
    }

    public void heartBeat(ConnectionParam connectionParam){
        Channel channel = channels.get(connectionParam.uniqueKey());
        if(channel != null && channel.isActive()){
            channel.attr(ConnectionParam.HEART_BEAT_TIME).set(LocalDateTime.now());
        }
    }
    public void connect(ConnectionParam connectionParam, Channel channel){

        if(connectionParam == null){
            return;
        }
        channels.put(connectionParam.uniqueKey(),channel);
    }

    public Channel getConnection(ConnectionParam connectionParam){
        return channels.get(connectionParam.uniqueKey());
    }

    public void close(ConnectionParam connectionParam){
        if(connectionParam == null){
            return;
        }
        close(connectionParam.uniqueKey());
    }

    public void close(String uniqueKey){
        channels.remove(uniqueKey);
    }
}
