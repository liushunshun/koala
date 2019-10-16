package com.koala.gateway.dto;

import lombok.Data;
import org.springframework.context.annotation.Import;

/**
 * @author XiuYang
 * @date 2019/10/15
 */

@Data
@Import(value = KoalaMessageSendRequest.class)
public class KoalaMessageSendRequest {

    private String msgType;

    private String content;

    private String senderId;

    private String clientMessageId;

}
