package com.koala.gateway.server.handler;

import com.koala.gateway.enums.RequestType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XiuYang
 * @date 2020/11/02
 */

public class HttpRequestHandlerFactory {

    public static final Map<RequestType, RequestHandler> httpRequestHandlerMap = new ConcurrentHashMap<>();

    public static void register(RequestHandler requestHandler){
        httpRequestHandlerMap.put(requestHandler.getRequestType(),requestHandler);
    }

    public static RequestHandler getHandler(RequestType requestType){
        if(requestType == null){
            return null;
        }
        return httpRequestHandlerMap.get(requestType);
    }
}
