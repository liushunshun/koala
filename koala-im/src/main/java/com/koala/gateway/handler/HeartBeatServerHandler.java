package com.koala.gateway.handler;

import com.koala.gateway.connection.Connection;
import com.koala.gateway.dto.HeartBeatRequest;
import com.koala.gateway.dto.HeartBeatResponse;

import java.util.concurrent.Executor;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class HeartBeatServerHandler implements ServerHandler<HeartBeatRequest> {


    @Override
    public void handleRequest(final HeartBeatRequest request, final Connection connection, final long startTime) {
        connection.writeResponse(new HeartBeatResponse(request.getRequestId()));
    }

    @Override
    public Executor getExecutor(final HeartBeatRequest request) {
        return null;
    }
}
