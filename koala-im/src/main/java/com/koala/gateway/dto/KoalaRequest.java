package com.koala.gateway.dto;

import com.koala.gateway.connection.ConnectionParam;
import lombok.Data;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Data
public abstract class KoalaRequest {

    /**
     * 请求ID
     */
    private Long requestId;

    /**
     * 协议类型，心跳、发送聊天消息、数据请求、消息推送
     */
    private String type;

    /**
     * 连接参数
     */
    private ConnectionParam connectionParam;

    public abstract List<String> invalidParams();

}
