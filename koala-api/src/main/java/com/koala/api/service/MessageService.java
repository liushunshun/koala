package com.koala.api.service;

import com.koala.api.dto.MessageSendParam;
import com.koala.api.dto.MessageSendResult;
import com.koala.api.dto.Result;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
public interface MessageService {

    /**
     * 发送消息
     *
     * @param param
     * @return
     */
    Result<MessageSendResult> send(MessageSendParam param);
}
