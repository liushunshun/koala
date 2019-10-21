package com.koala.core.message.service;

import com.koala.core.common.dto.Result;
import com.koala.core.message.dto.MessagePushParam;
import com.koala.core.message.dto.MessageSendParam;
import com.koala.core.message.dto.MessageSendResponse;

/**
 * @author XiuYang
 * @date 2019/10/21
 */

public interface MessageSendService {

    /**
     * 聊天消息发送
     * @param param
     * @return
     */
    Result<MessageSendResponse> sendChatMessage(MessageSendParam param);


    /**
     * 发送系统消息
     * @param param
     * @return
     */
    Result<MessageSendResponse> sendSystemMessage(MessageSendParam param);


    /**
     * 推送消息给用户
     *
     * @param param
     * @return
     */
    Result<MessageSendResponse> push(MessagePushParam param);
}
