package com.koala.route.push;

import com.alibaba.fastjson.JSON;
import com.koala.api.BizException;
import com.koala.api.dto.MessageDTO;
import com.koala.api.dto.Result;
import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.server.connection.ConnectionManager;
import com.koala.gateway.server.connection.ConnectionParam;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Slf4j
@Component
public class MessagePushManager {

    @Autowired
    private ConnectionManager connectionManager;

    public Result push(MessageDTO message){

        Result result = Result.create();

        try{
            Assert.noNullElements(new Object[]{message,message.getSenderId()},"发送人ID为空");

            //根据发送人ID获取链接
            Channel channel = connectionManager.getConnection(new ConnectionParam(message.getReceiverId()));

            if(channel == null){
                throw new BizException(ResponseStatus.CONNECTION_NOT_ONLINE);
            }

            if(! channel.isActive()){
                throw new BizException(ResponseStatus.CONNECTION_NOT_ACTIVEE);
            }

            //推送到链接
            ChannelFuture channelFuture = channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(message)));

            channelFuture.addListener((ChannelFutureListener)future -> {
                if (!future.isSuccess()){
                    log.error("MessagePushManager.push failed message={}",JSON.toJSONString(message));
                }else{
                    log.info("MessagePushManager.push success message={}",JSON.toJSONString(message));
                }
            });

            return result;

        }catch (Throwable e){
            log.error("MessagePushManager.push exception message={}",JSON.toJSONString(message),e);
            result.error(ResponseStatus.SYSTEM_EXCEPTION,e.getMessage());
        }
        return result;
    }
}
