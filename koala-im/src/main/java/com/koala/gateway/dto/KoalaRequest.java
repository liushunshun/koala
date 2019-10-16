package com.koala.gateway.dto;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Data
public class KoalaRequest {

    /**
     * 请求ID
     */
    private Long requestId;

    /**
     * 协议类型，心跳、发送聊天消息、数据请求、消息推送
     */
    private String protocolType;

    /**
     * 请求数据体
     */
    private JSONObject requestBody;

    public <T> T getRequestBody(Class<T> clazz){
        return requestBody.toJavaObject(clazz);
    }
}
