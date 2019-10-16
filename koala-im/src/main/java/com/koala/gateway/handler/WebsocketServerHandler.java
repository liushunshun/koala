package com.koala.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.KoalaRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class WebsocketServerHandler extends SimpleChannelInboundHandler<KoalaRequest> {

    @Autowired
    private HandlerFactory handlerFactory;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, KoalaRequest msg) throws Exception {
        KoalaHandler koalaHandler = handlerFactory.getHandler(msg.getType());

        KoalaResponse response = koalaHandler.process(msg);

        response.setRequestId(msg.getRequestId());
        response.setType(msg.getType());

        ctx.channel().writeAndFlush(JSON.toJSONString(response));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        log.error("客户端与服务端会话连接成功");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        log.error("客户端与服务端会话连接断开");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("服务端监听到客户端异常:",cause);
        ctx.close();
    }
}
