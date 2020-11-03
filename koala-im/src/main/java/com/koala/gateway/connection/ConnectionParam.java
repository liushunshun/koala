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

    private String path;
    private String token;

    public ConnectionParam(){
    }
    public ConnectionParam(String token){
        this.token = token;
    }

    public String uniqueKey(){
        return token;
    }
}
