package com.koala.gateway.connection;

import io.netty.util.AttributeKey;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Data
public class ConnectionParam {

    public static AttributeKey<ConnectionParam> CHANNEL_PARAM = AttributeKey.valueOf("CHANNEL_PARAM");
    public static AttributeKey<LocalDateTime> HEART_BEAT_TIME = AttributeKey.valueOf("HEART_BEAT_TIME");

    private String userId;

    public ConnectionParam(){
    }
    public ConnectionParam(String userId){
        this.userId = userId;
    }

    public String uniqueKey(){
        return userId;
    }
}
