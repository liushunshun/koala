package com.koala.gateway.handler.core;

import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.listener.connection.ConnectionListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
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
public class ConnectionHandler  extends SimpleChannelInboundHandler<Object> {

    @Autowired
    private List<ConnectionListener> connectionListeners;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        ConnectionParam connectionParam = ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).get();

        if(CollectionUtils.isNotEmpty(connectionListeners)){
            connectionListeners.forEach(listener -> {
                try{
                    listener.connect(connectionParam,ctx.channel());
                }catch (Exception e){
                    log.error("ConnectionHandler channelActive call listener exception listener = {} ,connectionParam ={}", listener,connectionParam,e);
                }
            });
        }

        log.error("客户端与服务端会话连接成功 connectionParam={}",connectionParam);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);

        ConnectionParam connectionParam = ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).get();
        if(CollectionUtils.isNotEmpty(connectionListeners)){
            connectionListeners.forEach(listener -> {
                try{
                    listener.close(connectionParam);
                }catch (Exception e){
                    log.error("ConnectionHandler channelInactive call listener exception listener = {} ,connectionParam ={}", listener,connectionParam,e);
                }
            });
        }

        log.error("客户端与服务端会话连接断开 connectionParam={}",connectionParam);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {

    }
}
