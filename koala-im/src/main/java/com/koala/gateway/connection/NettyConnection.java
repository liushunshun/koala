package com.koala.gateway.connection;

import com.koala.gateway.dto.BaseResponse;
import com.koala.gateway.server.NettyServer;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.text.MessageFormat;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@Slf4j
public class NettyConnection implements Connection {
    private final Channel channel;
    private final String peerIP;

    private volatile long lastReadTime = System.currentTimeMillis();

    public NettyConnection(final Channel channel) {
        this.channel = channel;
        this.peerIP = ((InetSocketAddress) channel.remoteAddress()).getAddress().getHostAddress();
    }

    @Override
    public SocketAddress getRemoteAddress() {
        return channel.remoteAddress();
    }

    @Override
    public SocketAddress getLocalAddress() {
        return channel.localAddress();
    }

    @Override
    public String getPeerIP() {
        return peerIP;
    }

    @Override
    public String toString() {
        return MessageFormat.format("L:{0},R:{1}", this.getLocalAddress(), this.getRemoteAddress());
    }

    @Override
    public void refreshLastReadTime(long lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    @Override
    public long getLastReadTime() {
        return lastReadTime;
    }

    @Override
    public void writeResponse(BaseResponse response) {
        doWrite(response,new CloseOnFailureListener());
    }

    @Override
    public void close() {
        this.channel.close();
    }

    private void doWrite(Object response, ChannelFutureListener listener) {
        if (response != null) {
            if (channel.isWritable()) {
                ChannelFuture wf = channel.writeAndFlush(response);
                wf.addListener(listener);
            }else{
                log.error("write overflow : server give up write response : " + channel + " , remote ip is [ " + NettyConnection.this.getPeerIP() + "]");
            }
        }

    }

    private class CloseOnFailureListener implements ChannelFutureListener {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (!future.isSuccess()) {
                log.error("server write response error: " + channel + " , remote ip is [ " + NettyConnection.this.getPeerIP() + "]"
                    + ((null != future.cause()) ? ", cause:" + future.cause() : ""));
                // need response or not under this condition?
                // this.sendErrorResponse(NettyConnection.this, request);
                if (!channel.isActive()) {
                    channel.close();
                }
            }
        }
    }

    private class HttpFailureListener extends CloseOnFailureListener {
        private boolean isKeepAlive;

        HttpFailureListener(boolean isKeepAlive) {
            this.isKeepAlive = isKeepAlive;
        }

        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            super.operationComplete(future);
            if (!channel.isOpen() || !isKeepAlive) {
                channel.close();
            }
        }
    }
}
