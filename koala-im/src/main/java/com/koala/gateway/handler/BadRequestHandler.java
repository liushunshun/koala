package com.koala.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.koala.gateway.connection.ConnectionParam;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class BadRequestHandler extends SimpleChannelInboundHandler {

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            WebSocketServerProtocolHandler.HandshakeComplete complete = (WebSocketServerProtocolHandler.HandshakeComplete) evt;


            log.error("客户端与服务端会话连接成功 uri={}",complete.requestUri());
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.warn("base response handler: can not find any handler to process this request");
        ctx.channel().writeAndFlush(new TextWebSocketFrame("error")).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("base response handler: exception caught", cause);
        ctx.channel().writeAndFlush(new TextWebSocketFrame("error")).addListener(ChannelFutureListener.CLOSE);
        super.exceptionCaught(ctx, cause);
    }

}