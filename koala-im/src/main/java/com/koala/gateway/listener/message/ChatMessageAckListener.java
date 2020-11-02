package com.koala.gateway.listener.message;

import com.koala.gateway.dto.KoalaAckRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.route.ack.MessageAckManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component("CHAT_MSG_ACK")
public class ChatMessageAckListener implements MessageListener {

    @Autowired
    private MessageAckManager messageAckManager;

    /**
     * @param koalaRequest
     * @return
     */
    @Override
    public KoalaResponse receive(KoalaRequest koalaRequest) {

        KoalaAckRequest messageAckRequest = (KoalaAckRequest)koalaRequest;

        if(CollectionUtils.isNotEmpty(messageAckRequest.getMessageIds())){
             MessageAckManager.unAckSet.removeAll(messageAckRequest.getMessageIds());
        }
        log.info("ChatMessageAckHandler ack message success messageAckRequest={}",messageAckRequest);

        return new KoalaResponse();
    }

}
