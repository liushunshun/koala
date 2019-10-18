package com.koala.gateway.tcp;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

@Slf4j
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<BaseRequest> {

    public static final ConcurrentMap<Channel, NettyConnection> channels = new ConcurrentHashMap<>();

    private AtomicInteger connectionNum = new AtomicInteger();

    public NettyServerHandler(){
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            System.out.println("connections: " + connectionNum.get());
        }, 0, 2, TimeUnit.SECONDS);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("",cause);
        super.exceptionCaught(ctx, cause);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channels.putIfAbsent(channel, new NettyConnection(channel));

        connectionNum.incrementAndGet();
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.channel().close();
        channels.remove(ctx.channel());

        connectionNum.decrementAndGet();
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
