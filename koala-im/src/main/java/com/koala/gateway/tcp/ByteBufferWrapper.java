package com.koala.gateway.tcp;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public interface ByteBufferWrapper {

    void writeByte(int index, byte data);

    void writeByte(byte data);

    byte readByte();

    void writeInt(int data);

    void writeBytes(byte[] data);

    int readableBytes();

    int readInt();

    void readBytes(byte[] dst);

    int readerIndex();

    void setReaderIndex(int readerIndex);

    void writeLong(long id);

    long readLong();

    void ensureCapacity(int capacity);

}
