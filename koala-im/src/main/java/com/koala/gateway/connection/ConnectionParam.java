package com.koala.gateway.connection;

import io.netty.util.AttributeKey;
import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Data
public class ConnectionParam {

    public static AttributeKey<ConnectionParam> CHANNEL_PARAM = AttributeKey.valueOf("CHANNEL_PARAM");

    private String userId;

    public String uniqueKey(){
        return userId;
    }
}
