package com.koala.test;

import com.koala.gateway.dto.HeartBeatRequest;
import com.koala.gateway.dto.HeartBeatResponse;
import com.koala.gateway.encoder.NettyByteBufferWrapper;
import com.koala.gateway.encoder.NettyProtocolDecoder;
import com.koala.gateway.encoder.NettyProtocolEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Alex on 2019/9/30.
 */
@Slf4j
public class Main {

    private static final Map<Long,Channel> channelKeeper = new ConcurrentHashMap<>();

    public static void main(String[] args) throws Exception{
        Main main = new Main();



        main.start();


    }

    public void start(){
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup(1);
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY,true)
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

        for(long i = 1;i<1000000;i++) {
            final long userId = i;
            ChannelFuture channelFuture = bootstrap.connect("39.100.71.220", 8888);
            channelFuture.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    if(channelFuture.isSuccess()){
                        channelKeeper.put(userId,channelFuture.channel());
                        channelFuture.channel().writeAndFlush(new HeartBeatRequest(userId,100));
                        log.warn("current connection userId={} connect success,size : {}",userId,channelKeeper.size());
                    }else{
                        log.error("connect failed userId={}",userId);
                    }
                }
            });
        }


    }
}
