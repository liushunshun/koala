package com.koala.gateway.encoder;

import com.alibaba.fastjson.JSON;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.initializer.WebSocketChannelInitializer;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Slf4j
@Component
public class WebSocketJsonEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise){
        if(msg instanceof WebSocketFrame){
            ctx.writeAndFlush(msg, promise);
            return;
        }else if(msg instanceof KoalaResponse){
            ConnectionParam connectionParam = ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).get();
            if(connectionParam == null){
                return;
            }
            if(Objects.equals(connectionParam.getProtocol(),ConnectionParam.WS)){
                ctx.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(msg)), promise);
            }else{
                ctx.writeAndFlush(JSON.toJSONString(msg), promise).addListener(ChannelFutureListener.CLOSE);
            }
        }else{
            ctx.writeAndFlush(msg, promise);
        }

    }
}
