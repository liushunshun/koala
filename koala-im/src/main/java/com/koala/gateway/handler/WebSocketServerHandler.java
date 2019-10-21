package com.koala.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.koala.gateway.connection.ConnectionManager;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.enums.EnumRequestType;
import com.koala.gateway.enums.EnumResponseStatus;
import com.koala.gateway.listener.message.MessageListener;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

        MessageListener messageListener = listenerMap.get(msg.getType());

        KoalaResponse response = messageListener.receive(msg);

        response.setRequestId(msg.getRequestId());
        response.setType(msg.getType());

        ctx.channel().writeAndFlush(JSON.toJSONString(response));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause){
        log.warn("WebSocketServerHandler exception",cause);
        ctx.channel().writeAndFlush(KoalaResponse.error(0L, EnumRequestType.CONNECTION.getCode(), EnumResponseStatus.AUTH_FAILED)).addListener(ChannelFutureListener.CLOSE);
        ctx.close();
        return;
    }
}
