package com.koala.gateway.dto;

import com.koala.gateway.enums.EnumResponseStatus;
import lombok.Builder;
import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/10/16
 */
@Data
@Builder
public class KoalaResponse<T> {

    /**
     * 请求ID
     */
    private Long requestId;

    /**
     * 协议类型，心跳、发送聊天消息、数据请求、消息推送
     */
    private String type;

    private boolean ok = true;

    private Integer errorCode;

    private String errorMessage;

    private T data;

    public KoalaResponse() {
    }

    public KoalaResponse(Long requestId, String type) {
        this.requestId = requestId;
        this.type = type;
    }

    public KoalaResponse(Long requestId, String type, boolean ok, Integer errorCode, String errorMessage, T data) {
        this.requestId = requestId;
        this.type = type;
        this.ok = ok;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.data = data;
    }

    public static KoalaResponse error(Long requestId, String type, EnumResponseStatus responseStatus){
        return KoalaResponse.builder().ok(false).requestId(requestId).type(type)
            .errorCode(responseStatus.getCode()).errorMessage(responseStatus.getDescription()).build();
    }

    public static KoalaResponse error(Long requestId, String type, EnumResponseStatus responseStatus,String errorMessage){
        return KoalaResponse.builder().ok(false).requestId(requestId).type(type)
            .errorCode(responseStatus.getCode()).errorMessage(responseStatus.getDescription() + " " +errorMessage).build();
    }
}
