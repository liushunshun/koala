package com.koala.service;

import com.alibaba.fastjson.JSON;
import com.koala.api.BizException;
import com.koala.api.dto.MessageDTO;
import com.koala.api.dto.MessageSendParam;
import com.koala.api.dto.MessageSendResult;
import com.koala.api.dto.Result;
import com.koala.api.enums.ResponseStatus;
import com.koala.api.service.MessageService;
import com.koala.gateway.connection.ConnectionManager;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.route.ack.AckTask;
import com.koala.route.ack.MessageAckManager;
import com.koala.route.push.MessagePushManager;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.UUID;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService{

    @Autowired
    private MessagePushManager messagePushManager;

    @Autowired
    private MessageAckManager messageAckManager;

    @Override
    public Result<MessageSendResult> send(MessageSendParam param) {

        Result<MessageSendResult> result = Result.create();

        try{
            Assert.noNullElements(new Object[]{param,param.getMessage()},"消息内容为空");

            MessageDTO message = param.getMessage();

            //如果没有ID则生成ID
            if(message.getMid() == null){
                message.setMid(UUID.randomUUID().toString().replace("-",""));
            }

            Assert.notNull(message.getSenderId(),"发送人ID为空");
            Assert.notNull(message.getReceiverId(),"接收人ID为空");

            Result pushResult = messagePushManager.push(message);

            //定时检测未ack的重新推送
            messageAckManager.checkAck(message);

            pushResult.setData(new MessageSendResult(message.getMid()));

            return pushResult;

        }catch (Throwable e){
            log.error("MessageService.send exception param={}",JSON.toJSONString(param),e);
            result.error(ResponseStatus.SYSTEM_EXCEPTION,e.getMessage());
        }
        return result;
    }
}
