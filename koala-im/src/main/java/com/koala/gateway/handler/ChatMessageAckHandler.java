package com.koala.gateway.handler;

import com.koala.gateway.dto.KoalaMessageAckRequest;
import com.koala.gateway.dto.KoalaMessageSendRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component("CHAT_MSG_ACK")
public class ChatMessageAckHandler implements KoalaHandler{

    /**
     * {
         "requestId": 123,
         "type": "CHAT_MSG_SEND",
         "msgType": "text",
         "content": "hello",
         "senderId": "2123",
         "sessionId": "123123",
         "clientMessageId": "SDFSDFSDFSFSDFsdf"
        }

     {
         "requestId": 123,
         "type": "CHAT_MSG_ACK",
         "messageIds":[
         "123123",
         "223sdlfsd"
         ],
         "sessionId": "123123",
         "clientMessageId": "SDFSDFSDFSFSDFsdf"
     }
     * @param koalaRequest
     * @return
     */
    @Override
    public KoalaResponse process(KoalaRequest koalaRequest) {

        KoalaMessageAckRequest messageAckRequest = (KoalaMessageAckRequest)koalaRequest;

        log.info("ChatMessageAckHandler ack message success messageAckRequest={}",messageAckRequest);

        return new KoalaResponse();
    }

}
