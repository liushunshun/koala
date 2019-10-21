package com.koala.gateway.handler;

import com.koala.chat.message.service.MessageSendService;
import com.koala.gateway.dto.KoalaMessageSendRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.handler.core.KoalaHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component("CHAT_MSG_SEND")
public class ChatMessageSendHandler implements KoalaHandler {

    @Autowired
    private MessageSendService messageSendService;

    @Override
    public KoalaResponse process(KoalaRequest koalaRequest) {

        KoalaMessageSendRequest messageSendRequest = (KoalaMessageSendRequest)koalaRequest;

        //messageSendService.sendChatMessage()

        log.info("ChatMessageSendHandler send message success messageSendRequest={}",messageSendRequest);

        return new KoalaResponse();
    }

}
