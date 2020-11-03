package com.koala.gateway.server;

import com.koala.gateway.server.handler.WebSocketChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@Slf4j
@Component
public class NettyServer {

    private final AtomicBoolean startFlag = new AtomicBoolean(false);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);

    @Autowired
    private WebSocketChannelInitializer webSocketChannelInitializer;

    public void start(int port) throws Exception{
        if(!startFlag.compareAndSet(false,true)){
            return;
        }
        ServerBootstrap bootstrap = new ServerBootstrap()
            .group(bossGroup, workerGroup)
            .handler(new LoggingHandler(LogLevel.DEBUG))
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 2048)
            .option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
            .childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_REUSEADDR, true)
            .childHandler(webSocketChannelInitializer);

        ChannelFuture future = bootstrap.bind(new InetSocketAddress(port)).sync();
        if (future.isSuccess()) {
            log.warn("netty Server started: ws://127.0.0.1:" + port + "/ws");
        } else {
            log.error("netty server start failed , system exit!");
            System.exit(0);
        }
        future.channel().closeFuture().sync();
    }



    /**
     * 关闭
     */
    public void shutdown() {
        if (bossGroup != null) {
            bossGroup.shutdownGracefully();
        }
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
        }
    }
}
