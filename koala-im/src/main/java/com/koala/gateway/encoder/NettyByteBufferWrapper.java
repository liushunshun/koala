package com.koala.gateway.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class NettyByteBufferWrapper implements ByteBufferWrapper {

    private final ByteBuf buffer;
    private final Channel channel;

    public NettyByteBufferWrapper(ByteBuf buffer) {
        this(buffer, null);
    }

    public NettyByteBufferWrapper(ByteBuf buffer, Channel channel) {
        this.channel = channel;
        this.buffer = buffer;
    }

    @Override
    public byte readByte() {
        return buffer.readByte();
    }

    @Override
    public void readBytes(byte[] dst) {
        buffer.readBytes(dst);
    }

    @Override
    public int readInt() {
        return buffer.readInt();
    }

    @Override
    public int readableBytes() {
        return buffer.readableBytes();
    }

    @Override
    public int readerIndex() {
        return buffer.readerIndex();
    }

    @Override
    public void setReaderIndex(int index) {
        buffer.setIndex(index, buffer.writerIndex());
    }

    @Override
    public void writeByte(byte data) {
        buffer.writeByte(data);
    }

    @Override
    public void writeBytes(byte[] data) {
        buffer.writeBytes(data);
    }

    @Override
    public void writeInt(int data) {
        buffer.writeInt(data);
    }

    @Override
    public void writeByte(int index, byte data) {
        buffer.writeByte(data);
    }

    @Override
    public void writeLong(long value) {
        buffer.writeLong(value);
    }

    @Override
    public long readLong() {
        return buffer.readLong();
    }

}
