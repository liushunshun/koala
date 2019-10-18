package com.koala.gateway.tcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@Slf4j
public class NettyProtocolDecoder extends ByteToMessageDecoder{

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        NettyByteBufferWrapper wrapper = new NettyByteBufferWrapper(in, ctx.channel());
        Object msg = this.decode(wrapper);
        if (msg != null) {
            out.add(msg);
        }
    }

    private Object decode(ByteBufferWrapper wrapper) throws Exception {
        final int originPos = wrapper.readerIndex();
        if (wrapper.readableBytes() < 1) {
            wrapper.setReaderIndex(originPos);
            return null;
        }
        EnumProtocolType protocolType = EnumProtocolType.getEnum(wrapper.readByte());
        Protocol protocol = ProtocolFactory.instance.getProtocol(protocolType);
        if (protocol == null) {
            log.error("NettyProtocolDecoder protocol {} not support",protocol);
            return null;
        }

        return protocol.decode(wrapper, originPos);
    }
}
