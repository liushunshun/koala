package com.koala.core.message.service.impl;

import com.koala.core.common.dto.Result;
import com.koala.core.message.dto.MessagePushParam;
import com.koala.core.message.dto.MessageSendParam;
import com.koala.core.message.dto.MessageSendResponse;
import com.koala.core.message.service.MessageSendService;
import org.springframework.stereotype.Service;

/**
 * @author XiuYang
 * @date 2019/10/21
 */
@Service
public class MessageSendServiceImpl implements MessageSendService{

    @Override
    public Result<MessageSendResponse> sendChatMessage(MessageSendParam param) {

        //参数校验

        //会话类型识别

        //数据合法性校验

        //权限校验

        //会话状态校验

        return new Result<>();
    }

    @Override
    public Result<MessageSendResponse> sendSystemMessage(MessageSendParam param) {
        return null;
    }

    @Override
    public Result<MessageSendResponse> push(MessagePushParam param) {
        return null;
    }
}
