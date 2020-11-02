package com.koala.api.dto;

import lombok.Data;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Data
public class MessageSendParam {

    private MessageDTO message;

    private String sendApp;

}
