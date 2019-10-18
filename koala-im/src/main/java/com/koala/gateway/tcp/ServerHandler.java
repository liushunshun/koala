package com.koala.gateway.tcp;

import com.koala.gateway.tcp.Connection;
import com.koala.gateway.tcp.BaseRequest;

import java.util.concurrent.Executor;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public interface ServerHandler <T extends BaseRequest> {

    void handleRequest(final T request, final Connection connection, final long startTime);

    Executor getExecutor(final T request);

}