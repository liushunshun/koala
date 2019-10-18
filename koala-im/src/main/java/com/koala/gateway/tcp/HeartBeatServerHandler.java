package com.koala.gateway.tcp;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executor;

/**
 * @author XiuYang
 * @date 2019/09/30
 */
@Slf4j
public class HeartBeatServerHandler implements ServerHandler<HeartBeatRequest> {


    @Override
    public void handleRequest(final HeartBeatRequest request, final Connection connection, final long startTime) {
        log.error("server connection null : "+NettyServerHandler.channels.size());
        connection.writeResponse(new HeartBeatResponse(request.getRequestId()));
    }

    @Override
    public Executor getExecutor(final HeartBeatRequest request) {
        return null;
    }
}
