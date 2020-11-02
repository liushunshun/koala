package com.koala.api.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Data
public abstract class MessageDTO {

    private Map<String,Object> head;

    private String mid;

    private String msgType;

    private String sid;

    private String senderId;

    private String receiverId;

    private Long createTime;

    private Map<String,Object> extInfo;

    /**
     * 获取消息体
     *
     * @return
     */
    abstract Map<String,Object> getBody();

    /**
     * 还原消息体
     * @param body
     */
    abstract void setBody(String body);

}
