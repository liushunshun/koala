package com.koala.gateway.handler.message;

import com.koala.api.BizException;
import com.koala.api.dto.Result;
import com.koala.gateway.server.connection.ConnectionManager;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.enums.RequestType;
import com.koala.gateway.server.handler.HttpRequestHandlerFactory;
import com.koala.gateway.server.handler.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 *
 * @author XiuYang
 * @date 2019/10/16
 */
@Slf4j
@Component
public class HeartBeatMessageHandler implements RequestHandler {

    @Autowired
    private ConnectionManager connectionManager;

    @Override
    public RequestType getRequestType() {
        return RequestType.HEART_BEAT;
    }

    @Override
    public Result handle(KoalaRequest koalaRequest) throws BizException {
        log.info("HeartBeatMessageHandler ping koalaRequest={}",koalaRequest);

        connectionManager.heartBeat(koalaRequest.getConnectionParam());

        return new Result();
    }

    @Override
    public void afterPropertiesSet() {
        HttpRequestHandlerFactory.register(this);
    }
}
