package com.koala.gateway.listener.message;

import com.koala.gateway.dto.KoalaMessageSendRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component("CHAT_MSG_SEND")
public class ChatMessageSendListener implements MessageListener {

    @Override
    public KoalaResponse receive(KoalaRequest koalaRequest) {

        KoalaMessageSendRequest messageSendRequest = (KoalaMessageSendRequest)koalaRequest;

        log.info("ChatMessageSendHandler send message success messageSendRequest={}",messageSendRequest);

        return new KoalaResponse();
    }

}
