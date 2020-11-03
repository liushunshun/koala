package com.koala.gateway.server.handler;

import com.alibaba.fastjson.JSON;
import com.koala.api.dto.Result;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.utils.NettyResponseUtil;
import io.netty.channel.*;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class NettyResponseHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){

        log.info("执行：WebSocketJsonEncoder");

        if(msg instanceof WebSocketFrame){
            ctx.writeAndFlush(msg, promise);
            return;
        }else if(msg instanceof Result){
            ConnectionParam connectionParam = ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).get();
            if(connectionParam == null){
                return;
            }
            if(Objects.equals(connectionParam.getProtocol(),ConnectionParam.WS)){
                ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)), promise);
            }else{
                ctx.writeAndFlush(NettyResponseUtil.httpResponseJson(JSON.toJSONString(msg))).addListener(ChannelFutureListener.CLOSE);
            }

        }else{
            ctx.writeAndFlush(msg, promise);
        }

    }
}
