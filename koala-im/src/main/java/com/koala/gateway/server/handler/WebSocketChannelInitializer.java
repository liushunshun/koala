package com.koala.gateway.server.handler;

import com.koala.gateway.constants.GatewayConstants;
import com.koala.gateway.encoder.HttpPostParamDecoder;
import com.koala.gateway.encoder.WebSocketJsonDecoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Slf4j
@Component
public class WebSocketChannelInitializer extends ChannelInitializer<NioSocketChannel> {

    @Autowired
    private WebSocketServerHandler websocketServerHandler;

    @Autowired
    private BadRequestHandler badRequestHandler;

    @Autowired
    private AuthAndParseParamHandler authAndParamParseHandler;

    @Autowired
    private ConnectionHandler connectionHandler;

    @Autowired
    private HttpServerHandler httpServerHandler;

    @Autowired
    private NettyResponseHandler nettyResponseHandler;

    public static final String WS_URI = "/ws";

    /**
     * 初始化pipeline
     *
     * WebSocket数据压缩 pipeline.addLast(new WebSocketServerCompressionHandler());
     *
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(NioSocketChannel ch){
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new IdleStateHandler(5, 0, 0, TimeUnit.SECONDS));
        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(GatewayConstants.MAX_AGGREGATED_CONTENT_LENGTH));
        pipeline.addLast(new ChunkedWriteHandler());
        pipeline.addLast(authAndParamParseHandler);
        pipeline.addLast(new WebSocketServerProtocolHandler(WS_URI, null, true, GatewayConstants.MAX_FRAME_LENGTH,false,true));
        pipeline.addLast(connectionHandler);
        pipeline.addLast(new WebSocketJsonDecoder());
        pipeline.addLast(new HttpPostParamDecoder());
        pipeline.addLast(httpServerHandler);
        pipeline.addLast(websocketServerHandler);
        pipeline.addLast(nettyResponseHandler);
        pipeline.addLast(badRequestHandler);

    }


}
