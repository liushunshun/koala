package com.koala.core.server.config;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ServerChannel;
import lombok.Data;

/**
 * netty服务{@link com.koala.core.server.KoalaNettyServer}的配置
 *
 * @see com.koala.core.server.KoalaNettyServer
 */
@Data
public class KoalaNettyServerConfig {

    /**
     * host
     */
    private String hostName;

    /**
     * 服务端口
     */
    private Integer port;

    /**
     * netty的parentEventLoop的groupName
     */
    private String parentGroupName;

    /**
     * netty的parentEventLoop的线程数量
     */
    private Integer parentThreadNum;

    /**
     * netty的childEventLoop的groupName
     */
    private String childGroupName;

    /**
     * netty的childEventLoop的线程数量
     */
    private Integer childThreadNum;

    /**
     * netty服务的channel
     */
    private Class<? extends ServerChannel> serverChannelType;

    /**
     * netty服务的serverChannelHandler
     */
    private ChannelHandler serverChannelHandler;

    /**
     * netty服务的childChannelHandler
     */
    private ChannelHandler childChannelHandler;

    public KoalaNettyServerConfig() {
        this.hostName = "localhost";
        this.port = 8668;
        this.parentGroupName = "parentGroupName";
        this.childGroupName = "childGroupName";
        this.childThreadNum = 2;
        this.parentThreadNum = 2;
    }

}
