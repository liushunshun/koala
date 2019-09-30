package com.koala.gateway.server;

import com.koala.gateway.encoder.NettyProtocolDecoder;
import com.koala.gateway.encoder.NettyProtocolEncoder;
import com.koala.gateway.handler.NettyServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
public class NettyServer {

    private final AtomicBoolean startFlag = new AtomicBoolean(false);

    private final EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private final EventLoopGroup workerGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);

    NettyServerHandler nettyServerHandler = new NettyServerHandler();

    public void start(int port){
        if(!startFlag.compareAndSet(false,true)){
            return;
        }
        ServerBootstrap bootstrap = new ServerBootstrap()
            .group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 2048)
            .option(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
            .childOption(ChannelOption.ALLOCATOR, UnpooledByteBufAllocator.DEFAULT)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_REUSEADDR, true)
            .childHandler(new ChannelInitializer<SocketChannel>(){
                @Override
                protected void initChannel(SocketChannel ch){
                    ch.pipeline()
                        .addLast("decoder", new NettyProtocolDecoder())
                        .addLast("encoder", new NettyProtocolEncoder())
                        .addLast("handler", nettyServerHandler);
                }
            })
            ;

        bootstrap.bind(new InetSocketAddress(port)).addListener(future -> {
            if (future.isSuccess()) {
                //log.info("raven-gateway websocket server start success on port:{}",
                //    nettyWebsocketPort);
            } else {
                //log.error("raven-gateway websocket server start failed!");
                System.exit(0);
            }
        });
    }

    public static void main(String[] args) {
        new NettyServer().start(8888);
    }
}
