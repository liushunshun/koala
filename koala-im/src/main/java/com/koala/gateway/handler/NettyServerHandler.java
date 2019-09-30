package com.koala.gateway.handler;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@ChannelHandler.Sharable
public class NettyServerHandler<BaseRequest> extends SimpleChannelInboundHandler<BaseRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseRequest msg){

        System.out.println(JSON.toJSONString(msg));
        System.out.println(msg.getClass());
    }
}
