package com.koala.gateway.handler.message;

import com.koala.api.BizException;
import com.koala.api.dto.MessageSendParam;
import com.koala.api.dto.MessageSendResult;
import com.koala.api.dto.Result;
import com.koala.api.dto.TextMessageDTO;
import com.koala.api.enums.MessageType;
import com.koala.api.enums.ResponseStatus;
import com.koala.api.service.MessageService;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.KoalaSendRequest;
import com.koala.gateway.dto.KoalaSendTextRequest;
import com.koala.gateway.enums.RequestType;
import com.koala.gateway.server.handler.HttpRequestHandlerFactory;
import com.koala.gateway.server.handler.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component("CHAT_MSG_SEND")
public class ChatMessageSendHandler implements RequestHandler {

    @Autowired
    private MessageService messageService;

    @Override
    public RequestType getRequestType() {
        return RequestType.CHAT_MSG_TEXT;
    }

    @Override
    public KoalaResponse handle(KoalaRequest koalaRequest) throws BizException {
        KoalaSendRequest messageSendRequest = (KoalaSendRequest)koalaRequest;

        Result<MessageSendResult> sendResult =  messageService.send(convertParam(messageSendRequest));

        log.info("ChatMessageSendHandler send message success messageSendRequest={},sendResult={}",messageSendRequest,sendResult);

        return new KoalaResponse(sendResult);
    }

    @Override
    public void afterPropertiesSet() {
        HttpRequestHandlerFactory.register(this);
    }

    private MessageSendParam convertParam(KoalaSendRequest sendRequest)throws BizException{
        MessageSendParam sendParam = new MessageSendParam();

        MessageType messageType = MessageType.getEnum(sendRequest.getMsgType());

        if(messageType == null){
            throw new BizException(ResponseStatus.INVALID_MSG_TYPE);
        }

        if(sendRequest instanceof KoalaSendTextRequest){
            TextMessageDTO messageDTO = new TextMessageDTO();
            messageDTO.setMsgType(sendRequest.getMsgType());
            messageDTO.setText(((KoalaSendTextRequest)sendRequest).getText());
            messageDTO.setSenderId(sendRequest.getSenderId());
            messageDTO.setReceiverId(sendRequest.getReceiverId());
            sendParam.setMessage(messageDTO);
        }

        sendParam.setSendApp(sendRequest.getSendApp());

        return sendParam;
    }


}
