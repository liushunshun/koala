package com.koala.gateway.encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.enums.EnumRequestType;
import com.koala.gateway.enums.EnumResponseStatus;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Slf4j
@Component
public class WebSocketJsonEncoder extends MessageToMessageEncoder<KoalaResponse> {

    @Override
    protected void encode(ChannelHandlerContext ctx, KoalaResponse msg, List<Object> out){
        out.add(new TextWebSocketFrame(JSON.toJSONString(msg)));
    }
}
