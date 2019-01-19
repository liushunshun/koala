package com.koala.core.server;

import com.koala.core.constant.KoalaCoreConstant;
import com.koala.core.logger.KoalaCoreLogger;
import com.koala.core.server.exception.KoalaServerException;
import org.slf4j.Logger;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 基础的IM服务实现
 */
public abstract class AbstractKoalaServer implements IKoalaServer {

    private static Logger logger;

    static {
        logger = KoalaCoreLogger.koalaCoreLogger;
    }

    /**
     * 服务名
     */
    protected String serverName;

    /**
     * 服务是否已启动
     */
    protected boolean started;

    /**
     *
     */
    protected ReentrantLock reentrantLock;

    protected AbstractKoalaServer() {
        this(KoalaCoreConstant.KoalaServer.DEFAULT_SERVER_NAME);
    }

    protected AbstractKoalaServer(String serverName) {
        this.serverName = serverName;
        this.reentrantLock = new ReentrantLock();
        this.started = false;
    }

    @Override
    public String serverName() {
        return this.serverName;
    }

    @Override
    public void serverName(String serverName) {
        this.serverName = serverName;
    }

    @Override
    public boolean isStarted() {
        return this.started;
    }

    @Override
    public void start() throws KoalaServerException {
        //  启动服务
        //  1、尝试获取锁
        boolean lock = this.reentrantLock.tryLock();
        if (!lock) {
            //  没有获取到就不做处理了
            return;
        }

        try {
            //  2、启动服务
            this.doStart();
        } catch (Throwable throwable) {
            logger.error("start koala server exception", throwable);
            throw new KoalaServerException(throwable);
        } finally {
            //  无论如何释放锁
            this.reentrantLock.unlock();
        }
    }

    @Override
    public void stop() throws KoalaServerException {
        //  停止服务
        //  1、尝试获取锁
        boolean lock = this.reentrantLock.tryLock();
        if (!lock) {
            //  没有获取到就不做处理了
            return;
        }

        try {
            //  2、启动服务
            this.doStop();
        } catch (Throwable throwable) {
            logger.error("stop koala server exception", throwable);
            throw new KoalaServerException(throwable);
        } finally {
            //  无论如何释放锁
            this.reentrantLock.unlock();
        }
    }

    protected abstract void doStart() throws KoalaServerException;

    protected abstract void doStop() throws KoalaServerException;
}
