package com.koala.test;

import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by Alex on 2019/9/30.
 */
@Slf4j
public class ClientToHandler extends SimpleChannelInboundHandler{
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        log.error("client received msg : "+ JSON.toJSONString(o));
    }
}
