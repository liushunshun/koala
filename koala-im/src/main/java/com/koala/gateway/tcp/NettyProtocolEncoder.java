package com.koala.gateway.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class NettyProtocolEncoder extends MessageToByteEncoder {
    public NettyProtocolEncoder() {
        super(true);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object message, ByteBuf out) throws Exception {
        NettyByteBufferWrapper byteBufferWrapper = new NettyByteBufferWrapper(out);
        ((BaseHeader) message).encode(byteBufferWrapper);
    }
}
