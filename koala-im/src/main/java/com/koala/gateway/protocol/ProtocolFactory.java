package com.koala.gateway.protocol;

import com.koala.gateway.enums.EnumProtocolType;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class ProtocolFactory {

    private final Map<EnumProtocolType,Protocol> protocolHandlers = new HashMap<>();

    public static ProtocolFactory instance = new ProtocolFactory();

    public Protocol getProtocol(Enum ProtocolType) {
        return protocolHandlers.get(ProtocolType);
    }

    private ProtocolFactory(){
        protocolHandlers.put(EnumProtocolType.HEART_BEAT, new HeartBeatProtocol());
    }

}
