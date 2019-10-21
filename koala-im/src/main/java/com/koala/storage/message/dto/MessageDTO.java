package com.koala.storage.message.dto;

import lombok.Data;

import java.util.Map;

/**
 * @author XiuYang
 * @date 2019/10/21
 */
@Data
public class MessageDTO {

    /**
     * 会话ID
     */
    private String sessionId;

    /**
     * 消息类型
     */
    private String messageType;

    private String content;

    private Long createTime;

    private Long modifyTime;

    private String senderId;

    private String senderName;

    private Boolean systemMessage;

    private Map<String,Object> extInfo;

}
