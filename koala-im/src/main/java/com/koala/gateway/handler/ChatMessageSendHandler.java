package com.koala.gateway.handler;

import com.koala.gateway.dto.KoalaMessageSendRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.KoalaRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component("CHAT_MSG_SEND")
public class ChatMessageSendHandler implements KoalaHandler{

    @Override
    public KoalaResponse process(KoalaRequest koalaRequest) {

        KoalaMessageSendRequest messageSendRequest = (KoalaMessageSendRequest)koalaRequest;

        log.info("ChatMessageSendHandler send message success messageSendRequest={}",messageSendRequest);

        return new KoalaResponse();
    }

}
