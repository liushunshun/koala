package com.koala.gateway.dto;

import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Data
public class MessageSendParam{

    private String msgType;

    private String body;
}
