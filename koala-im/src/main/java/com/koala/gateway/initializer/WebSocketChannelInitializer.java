package com.koala.gateway.initializer;

import com.alibaba.fastjson.JSON;
import com.koala.gateway.constants.GatewayConstants;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.handler.BadRequestHandler;
import com.koala.gateway.handler.WebsocketServerHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Slf4j
@Component
public class WebSocketChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Autowired
    private WebsocketServerHandler websocketServerHandler;

    @Autowired
    private BadRequestHandler badRequestHandler;

    @Override
    protected void initChannel(NioSocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // HTTP请求的解码和编码
        pipeline.addLast(new HttpServerCodec());
        // 把多个消息转换为一个单一的FullHttpRequest或是FullHttpResponse，
        // 原因是HTTP解码器会在每个HTTP消息中生成多个消息对象HttpRequest/HttpResponse,HttpContent,LastHttpContent
        pipeline.addLast(new HttpObjectAggregator(GatewayConstants.MAX_AGGREGATED_CONTENT_LENGTH));
        // 主要用于处理大数据流，比如一个1G大小的文件如果你直接传输肯定会撑暴jvm内存的; 增加之后就不用考虑这个问题了
        pipeline.addLast(new ChunkedWriteHandler());
        // WebSocket数据压缩
        //pipeline.addLast(new WebSocketServerCompressionHandler());

        //
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws", null, true, GatewayConstants.MAX_FRAME_LENGTH));
        pipeline.addLast(new MessageToMessageDecoder<TextWebSocketFrame>() {
            @Override
            protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List<Object> out) throws Exception {
                String content = msg.text();
                try{
                    out.add(JSON.parseObject(content, KoalaRequest.class));
                }catch (Exception e){
                    ctx.channel().writeAndFlush("param invalid msg:"+content);
                    log.error("decode exception content="+content,e);
                }
            }
        });
        pipeline.addLast(new MessageToMessageEncoder<String>() {
            @Override
            protected void encode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
                out.add(new TextWebSocketFrame(msg));
            }
        });
        // websocket请求处理
        pipeline.addLast(websocketServerHandler);


        pipeline.addLast(badRequestHandler);

    }
}
