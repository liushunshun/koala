package com.koala.gateway.listener.message;

import com.koala.api.BizException;
import com.koala.api.dto.*;
import com.koala.api.enums.MessageType;
import com.koala.api.enums.ResponseStatus;
import com.koala.api.service.MessageService;
import com.koala.gateway.dto.KoalaSendRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.KoalaSendTextRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component("CHAT_MSG_SEND")
public class ChatMessageSendHandler implements MessageHandler {

    @Autowired
    private MessageService messageService;

    @Override
    public KoalaResponse receive(KoalaRequest koalaRequest) throws BizException{

        KoalaSendRequest messageSendRequest = (KoalaSendRequest)koalaRequest;

        Result<MessageSendResult> sendResult =  messageService.send(convertParam(messageSendRequest));

        log.info("ChatMessageSendHandler send message success messageSendRequest={},sendResult={}",messageSendRequest,sendResult);

        return new KoalaResponse(sendResult);
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
