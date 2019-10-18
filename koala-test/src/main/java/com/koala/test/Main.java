package com.koala.test;

import com.koala.gateway.tcp.HeartBeatRequest;
import com.koala.gateway.tcp.NettyProtocolDecoder;
import com.koala.gateway.tcp.NettyProtocolEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;


/**
 * Created by Alex on 2019/9/30.
 */
@Slf4j
public class Main {


    public static void main(String[] args) throws Exception {
        Main main = new Main();

        main.start(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));

    }

    public void start(String host, int beginPort, int nPort) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup(1);
        bootstrap.group(group)
            .channel(NioSocketChannel.class)
            .option(ChannelOption.TCP_NODELAY, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline()
                        //                                .addLast(new IdleStateHandler(100,100, 150))
                        .addLast(new NettyProtocolDecoder())
                        .addLast(new NettyProtocolEncoder())
                        .addLast(new ClientToHandler());
                }
            });

        int index = 0;
        int port;
        while (!Thread.interrupted()) {
            port = beginPort + index;
            try {
                ChannelFuture channelFuture = bootstrap.connect(host, port);
                channelFuture.addListener((ChannelFutureListener) future -> {
                    if (!future.isSuccess()) {
                        if(future instanceof DefaultChannelPromise){
                            System.out.println(future.cause());
                        }
                        System.out.println("连接失败, 退出!");
                        //System.exit(0);
                    } else {
                        channelFuture.channel().writeAndFlush(new HeartBeatRequest(1, 100));
                    }
                    }
                );
                channelFuture.get();
            } catch (Exception e) {
            }
            if (++index == nPort) {
                index = 0;
            }
        }
    }
}

