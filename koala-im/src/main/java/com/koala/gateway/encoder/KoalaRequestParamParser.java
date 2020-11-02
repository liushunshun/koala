package com.koala.gateway.encoder;

import com.alibaba.fastjson.JSON;
import com.koala.gateway.dto.KoalaAckRequest;
import com.koala.gateway.dto.KoalaLoginRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaSendTextRequest;
import com.koala.gateway.enums.RequestType;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author XiuYang
 * @date 2020/11/02
 */

public class KoalaRequestParamParser {

    public static final Map<RequestType,Class<? extends KoalaRequest>> requestParamClassMap = new ConcurrentHashMap<>();

    static {
        KoalaRequestParamParser.register(RequestType.CHAT_MSG_ACK, KoalaAckRequest.class);
        KoalaRequestParamParser.register(RequestType.CHAT_MSG_TEXT, KoalaSendTextRequest.class);
        KoalaRequestParamParser.register(RequestType.LOGIN, KoalaLoginRequest.class);
    }
    public static void register(RequestType requestType,Class<? extends KoalaRequest> clz){
        requestParamClassMap.put(requestType, clz);
    }

    public static KoalaRequest parseParam(RequestType requestType,String content){
        if(requestType == null || StringUtils.isBlank(content)){
            return null;
        }
        Class<? extends KoalaRequest> clz = requestParamClassMap.get(requestType);
        return JSON.parseObject(content,clz);
    }

}
