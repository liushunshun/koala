package com.koala.gateway.listener.message;

import com.koala.gateway.connection.ConnectionManager;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
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
public class HeartBeatMessageListener implements MessageListener {

    @Autowired
    private ConnectionManager connectionManager;

    @Override
    public KoalaResponse receive(KoalaRequest koalaRequest) {

        log.info("HeartBeatMessageListener ping koalaRequest={}",koalaRequest);

        connectionManager.heartBeat(koalaRequest.getConnectionParam());

        return new KoalaResponse();
    }

}
