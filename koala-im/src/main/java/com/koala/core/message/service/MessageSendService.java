package com.koala.core.message.service;

import com.koala.core.common.dto.Result;
import com.koala.core.message.dto.MessageSendParam;
import com.koala.core.message.dto.MessageSendResponse;

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
