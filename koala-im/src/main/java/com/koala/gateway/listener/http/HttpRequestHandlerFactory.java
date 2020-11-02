package com.koala.gateway.listener.http;

import com.koala.gateway.enums.RequestType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XiuYang
 * @date 2020/11/02
 */

public class HttpRequestHandlerFactory {

    public static final Map<RequestType,HttpRequestHandler> httpRequestHandlerMap = new ConcurrentHashMap<>();

    public static void register(HttpRequestHandler requestHandler){
        httpRequestHandlerMap.put(requestHandler.getRequestType(),requestHandler);
    }

    public static HttpRequestHandler getHandler(RequestType requestType){
        if(requestType == null){
            return null;
        }
        return httpRequestHandlerMap.get(requestType);
    }
}
