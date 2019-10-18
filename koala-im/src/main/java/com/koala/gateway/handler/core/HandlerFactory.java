package com.koala.gateway.handler.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XiuYang
 * @date 2019/10/16
 */
@Component
public class HandlerFactory {

    @Autowired
    private Map<String,KoalaHandler> handlerMap = new ConcurrentHashMap<>();

    public KoalaHandler getHandler(String protocolType){
        return handlerMap.get(protocolType);
    }
}
