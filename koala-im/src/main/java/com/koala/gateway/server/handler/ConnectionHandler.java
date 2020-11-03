package com.koala.gateway.server.handler;

import com.alibaba.fastjson.JSON;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.handler.connection.CustomConnectionHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/21
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class ConnectionHandler  extends ChannelInboundHandlerAdapter {

    @Autowired
    private List<CustomConnectionHandler> customConnectionHandlers;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {

        log.info("执行：ConnectionHandler userEventTriggered");

        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;

            ConnectionParam connectionParam = ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).get();

            if(CollectionUtils.isNotEmpty(customConnectionHandlers)){
                customConnectionHandlers.forEach(listener -> {
                    try{
                        listener.connect(connectionParam,ctx.channel());
                    }catch (Exception e){
                        log.error("ConnectionHandler channelActive call listener exception listener = {} ,connectionParam ={}", listener,connectionParam,e);
                    }
                });
            }
            log.error("客户端与服务端会话连接成功 uri={},connectionParam={}",complete.requestUri(), JSON.toJSONString(connectionParam));
        }
    }


    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        log.info("执行：ConnectionHandler channelInactive");

        ConnectionParam connectionParam = ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).get();
        if(CollectionUtils.isNotEmpty(customConnectionHandlers)){
            customConnectionHandlers.forEach(listener -> {
                try{
                    listener.close(connectionParam);
                }catch (Exception e){
                    log.error("ConnectionHandler channelInactive call listener exception listener = {} ,connectionParam ={}", listener,connectionParam,e);
                }
            });
        }

        log.error("客户端与服务端会话连接断开 connectionParam={}",connectionParam);

        super.channelInactive(ctx);
    }
}
