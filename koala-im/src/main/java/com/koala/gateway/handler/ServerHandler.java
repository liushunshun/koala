package com.koala.gateway.handler;

import com.koala.gateway.connection.Connection;
import com.koala.gateway.dto.BaseRequest;

import java.util.concurrent.Executor;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public interface ServerHandler <T extends BaseRequest> {

    void handleRequest(final T request, final Connection connection, final long startTime);

    Executor getExecutor(final T request);

}