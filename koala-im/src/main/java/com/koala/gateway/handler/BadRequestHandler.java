package com.koala.gateway.handler;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
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