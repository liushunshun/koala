package com.koala.core.server;

import com.alibaba.dubbo.common.extension.SPI;
import com.koala.core.server.exception.KoalaServerException;

/**
 * IM服务接口
 */
@SPI
public interface IKoalaServer {

    /**
     * 获取服务名
     *
     * @return 服务名
     */
    String serverName();

    /**
     * 设置服务名
     *
     * @param serverName 服务名
     */
    void serverName(String serverName);

    /**
     * IM服务是否已启动
     *
     * @return IM服务是否已启动
     */
    boolean isStarted();

    /**
     * 启动IM服务
     */
    void start() throws KoalaServerException;

    /**
     * 停止IM服务
     */
    void stop() throws KoalaServerException;

}
