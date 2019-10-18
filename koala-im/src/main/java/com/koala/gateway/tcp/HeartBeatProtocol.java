package com.koala.gateway.tcp;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class HeartBeatProtocol implements Protocol{

    public static final byte HEADER_LENGTH = 1 * 6 + 2 * 4;
    public static final byte VERSION = (byte) 1;
    public static final byte REQUEST = (byte) 0;
    public static final byte RESPONSE = (byte) 1;

    @Override
    public Object decode(ByteBufferWrapper wrapper, int originPos) throws Exception {
        if (wrapper.readableBytes() < HEADER_LENGTH - 1) {
            wrapper.setReaderIndex(originPos);
            return null;
        }
        byte type = wrapper.readByte();
        byte version = wrapper.readByte();
        if (version == VERSION) {
            if (type == REQUEST) {
                wrapper.readByte();
                wrapper.readByte();
                wrapper.readByte();
                long requestId = wrapper.readLong();
                int timeout = wrapper.readInt();
                HeartBeatRequest requestWrapper = new HeartBeatRequest(requestId, timeout);
                return requestWrapper;
            } else if (type == RESPONSE) {
                wrapper.readByte();
                wrapper.readByte();
                wrapper.readByte();
                long requestId = wrapper.readLong();
                wrapper.readInt();
                return new HeartBeatResponse(requestId);
            } else {
                throw new Exception("protocol type :" + type + " is not supported!");
            }
        } else {
            throw new Exception("protocol version :" + version + " is not supported!");
        }
    }

}
