package com.koala.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.koala.gateway.connection.Connection;
import com.koala.gateway.connection.NettyConnection;
import com.koala.gateway.dto.BaseRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executor;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseRequest> {

    public static final ConcurrentMap<Channel, NettyConnection> channels = new ConcurrentHashMap<>();


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.putIfAbsent(channel, new NettyConnection(channel));
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
        channels.remove(ctx.channel());
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseRequest message){

        final Connection connection = channels.get(ctx.channel());
        connection.refreshLastReadTime(System.currentTimeMillis());
        BaseRequest request = message;


        ServerHandler<BaseRequest> serverHandler = (ServerHandler<BaseRequest>) request.getServerHandler();
        //Executor executor = serverHandler.getExecutor(request);
        //if (executor == null) {
            serverHandler.handleRequest(request, connection, System.currentTimeMillis());
        //} else {
        //    try {
        //        executor.execute(new HandlerRunnable(connection, request, serverHandler));
        //    } catch (Throwable t) {
        //        LOGGER.error("", "Local HSF thread pool is full." + t.getMessage());
        //        BaseResponse responseWrapper = request.createErrorResponse("Provider's HSF thread pool is full.");
        //        responseWrapper.setStatus(ResponseStatus.THREADPOOL_BUSY);
        //        connection.writeResponse(responseWrapper);
        //    }
        //}
    }
}
