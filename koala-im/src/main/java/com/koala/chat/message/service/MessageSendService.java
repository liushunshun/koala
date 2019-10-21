package com.koala.chat.message.service;

import com.koala.chat.common.dto.Result;
import com.koala.chat.message.dto.MessageSendParam;
import com.koala.chat.message.dto.MessageSendResponse;

/**
 * @author XiuYang
 * @date 2019/10/21
 */

public interface MessageSendService {

    /**
     * 发送聊天消息
     * @param param
     * @return
     */
    Result<MessageSendResponse> sendChatMessage(MessageSendParam param);
}
