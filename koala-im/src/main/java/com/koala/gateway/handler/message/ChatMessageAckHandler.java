package com.koala.gateway.handler.message;

import com.koala.api.BizException;
import com.koala.api.dto.Result;
import com.koala.gateway.dto.KoalaAckRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.enums.RequestType;
import com.koala.gateway.server.handler.HttpRequestHandlerFactory;
import com.koala.gateway.server.handler.RequestHandler;
import com.koala.route.ack.MessageAckManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component
public class ChatMessageAckHandler implements RequestHandler {

    @Override
    public RequestType getRequestType() {
        return RequestType.CHAT_MSG_ACK;
    }

    @Override
    public Result handle(KoalaRequest koalaRequest) throws BizException {
        KoalaAckRequest messageAckRequest = (KoalaAckRequest)koalaRequest;

        if(CollectionUtils.isNotEmpty(messageAckRequest.getMessageIds())){
            MessageAckManager.unAckSet.removeAll(messageAckRequest.getMessageIds());
        }
        log.info("ChatMessageAckHandler ack message success messageAckRequest={}",messageAckRequest);

        return new Result();
    }

    @Override
    public void afterPropertiesSet() {
        HttpRequestHandlerFactory.register(this);
    }
}
