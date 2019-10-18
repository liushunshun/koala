package com.koala.gateway.tcp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class ProtocolFactory {

    private final Map<EnumProtocolType,Protocol> protocolHandlers = new HashMap<>(256);
    private final Map<EnumProtocolType,ServerHandler<?>> serverHandlers = new HashMap<>(256);

    public static ProtocolFactory instance = new ProtocolFactory();

    public Protocol getProtocol(Enum ProtocolType) {
        return protocolHandlers.get(ProtocolType);
    }

    public ServerHandler getServerHandler(Enum ProtocolType) {
        return serverHandlers.get(ProtocolType);
    }

    private ProtocolFactory(){
        protocolHandlers.put(EnumProtocolType.HEART_BEAT, new HeartBeatProtocol());
        serverHandlers.put(EnumProtocolType.HEART_BEAT, new HeartBeatServerHandler());
    }

}
