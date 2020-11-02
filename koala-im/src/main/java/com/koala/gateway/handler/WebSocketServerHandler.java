package com.koala.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.koala.api.BizException;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.enums.RequestType;
import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.listener.message.MessageListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebSocketServerHandler extends SimpleChannelInboundHandler<KoalaRequest> {

    @Autowired
    private Map<String,MessageListener> listenerMap = new ConcurrentHashMap<>();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, KoalaRequest msg){

        RequestType requestType = RequestType.getEnum(msg.getType());
        if(requestType == null){
            throw new IllegalArgumentException("invalid type");
        }

        List<String> illegalArguments = msg.invalidParams();

        if(CollectionUtils.isNotEmpty(illegalArguments)){
            log.warn("WebSocketServerHandler.channelRead0 check param failed msg={}",JSON.toJSONString(msg));
            throw new IllegalArgumentException("invalid "+ illegalArguments.toString());
        }

        MessageListener messageListener = listenerMap.get(msg.getType());

        KoalaResponse response;
        try{
            response = messageListener.receive(msg);
        }catch (BizException e){
            log.error("WebSocketServerHandler.channelRead0 receive exception msg={}",JSON.toJSONString(msg),e);
            response = new KoalaResponse(e.getCode(),e.getMessage());
        }catch (Exception e){
            log.error("WebSocketServerHandler.channelRead0 receive exception msg={}",JSON.toJSONString(msg),e);
            response = new KoalaResponse(ResponseStatus.SYSTEM_EXCEPTION);
        }
        response.setRequestId(msg.getRequestId());
        response.setType(msg.getType());

        ctx.channel().writeAndFlush(JSON.toJSONString(response));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        if(cause instanceof IllegalArgumentException){
            return;
        }
        log.warn("WebSocketServerHandler exception",cause);
        ctx.channel().writeAndFlush(KoalaResponse.response("", "", ResponseStatus.SYSTEM_EXCEPTION)).addListener(ChannelFutureListener.CLOSE);
        ctx.close();
        return;
    }
}
