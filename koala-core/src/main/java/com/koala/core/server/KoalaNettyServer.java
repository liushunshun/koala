package com.koala.core.server;

import com.koala.core.constant.KoalaCoreConstant;
import com.koala.core.logger.KoalaCoreLogger;
import com.koala.core.server.config.KoalaNettyServerConfig;
import com.koala.core.server.exception.KoalaServerException;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ServerChannel;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ThreadFactory;

/**
 * netty服务
 *
 * @see com.koala.core.server.IKoalaServer
 */
public class KoalaNettyServer extends AbstractKoalaServer {

    private static Logger logger;

    static {
        logger = KoalaCoreLogger.koalaCoreLogger;
    }

    private KoalaNettyServerConfig koalaNettyServerConfig;
    private Channel serverChannel;

    public KoalaNettyServer() {
        this(null);
    }

    public KoalaNettyServer(KoalaNettyServerConfig koalaNettyServerConfig) {
        this(KoalaCoreConstant.KoalaServer.DEFAULT_NETTY_SERVER_NAME, koalaNettyServerConfig);
    }

    public KoalaNettyServer(String serverName, KoalaNettyServerConfig koalaNettyServerConfig) {
        super(serverName);
        this.koalaNettyServerConfig = koalaNettyServerConfig;
    }

    @Override
    protected void doStart() throws KoalaServerException {
        try {
            if (null != this.serverChannel) {
                //  如果已经启动过了，那么返回。
                return;
            }
            //  启动netty服务器
            //  1、获取参数
            String hostName = this.koalaNettyServerConfig.getHostName();
            Integer port = this.koalaNettyServerConfig.getPort();

            String parentGroupName = this.koalaNettyServerConfig.getParentGroupName();
            Integer parentThreadNum = this.koalaNettyServerConfig.getParentThreadNum();

            String childGroupName = this.koalaNettyServerConfig.getChildGroupName();
            Integer childThreadNum = this.koalaNettyServerConfig.getChildThreadNum();

            Class<? extends ServerChannel> serverChannelType = this.koalaNettyServerConfig.getServerChannelType();
            ChannelHandler serverChannelHandler = this.koalaNettyServerConfig.getServerChannelHandler();
            ChannelHandler childChannelHandler = this.koalaNettyServerConfig.getChildChannelHandler();

            //  2、拼装服务器地址
            SocketAddress address = new InetSocketAddress(hostName, port);

            //  3、初始化netty配置
            ThreadFactory parentThreadFactory = new DefaultThreadFactory(parentGroupName);
            ThreadFactory childThreadFactory = new DefaultThreadFactory(childGroupName);

            EpollEventLoopGroup parentEventLoopGroup = new EpollEventLoopGroup(parentThreadNum, parentThreadFactory);
            EpollEventLoopGroup childEventLoopGroup = new EpollEventLoopGroup(childThreadNum, childThreadFactory);

            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .channel(serverChannelType)
                    .handler(serverChannelHandler)
                    .childHandler(childChannelHandler)
                    .group(parentEventLoopGroup, childEventLoopGroup);

            //  4、启动netty服务
            ChannelFuture channelFuture = serverBootstrap
                    .bind(address)
                    .sync();
            this.serverChannel = channelFuture.channel();
        } catch (Throwable throwable) {
            logger.error("start koala netty server exception", throwable);
            throw new KoalaServerException();
        }
    }

    @Override
    protected void doStop() throws KoalaServerException {
        try {
            if (null == this.serverChannel) {
                //  如果没有启动过，那么返回
                return;
            }
            this.serverChannel
                    .close()
                    .addListener(future -> this.serverChannel = null)
                    .sync();
        } catch (Throwable throwable) {
            logger.error("stop koala netty server exception", throwable);
            throw new KoalaServerException();
        }
    }

    public KoalaNettyServerConfig getKoalaNettyServerConfig() {
        return koalaNettyServerConfig;
    }

    public void setKoalaNettyServerConfig(KoalaNettyServerConfig koalaNettyServerConfig) {
        this.koalaNettyServerConfig = koalaNettyServerConfig;
    }
}
