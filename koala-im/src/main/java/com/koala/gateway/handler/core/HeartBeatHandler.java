package com.koala.gateway.handler.core;

import com.koala.gateway.connection.ConnectionManager;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.dto.KoalaMessageAckRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.handler.core.KoalaHandler;
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
@Component("HEART_BEAT")
public class HeartBeatHandler implements KoalaHandler {

    @Autowired
    private ConnectionManager connectionManager;

    @Override
    public KoalaResponse process(KoalaRequest koalaRequest) {

        log.info("HeartBeatHandler ping koalaRequest={}",koalaRequest);

        connectionManager.heartBeat(koalaRequest.getConnectionParam());

        return new KoalaResponse(koalaRequest.getRequestId(),koalaRequest.getType());
    }

}
