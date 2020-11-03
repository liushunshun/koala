package com.koala.gateway.dto;

import com.koala.gateway.server.connection.ConnectionParam;
import io.netty.channel.Channel;
import lombok.Data;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Data
public abstract class KoalaRequest{

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 协议类型，心跳、发送聊天消息、数据请求、消息推送
     */
    private String requestType;

    /**
     * 连接参数
     */
    private ConnectionParam connectionParam;

    private Channel channel;

    public abstract List<String> invalidParams();

}
