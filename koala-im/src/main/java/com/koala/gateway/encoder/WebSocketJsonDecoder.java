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
public class WebSocketJsonDecoder extends MessageToMessageDecoder<TextWebSocketFrame> {

    @Override
    protected void decode(ChannelHandlerContext ctx, TextWebSocketFrame msg, List out) throws Exception {
        String content = msg.text();
        Long requestId = null;
        String type = "";
        try{
            JSONObject jsonObject = JSON.parseObject(content);
            type = jsonObject.getString("type");
            requestId = jsonObject.getLong("requestId");

            EnumRequestType requestType = EnumRequestType.getEnum(type);
            if(requestType == null){
                throw new IllegalArgumentException("invalid type");
            }
            if(requestId == null){
                throw new IllegalArgumentException("invalid requestId");
            }

            KoalaRequest koalaRequest = (KoalaRequest)jsonObject.toJavaObject(requestType.getDtoClazz());

            if(koalaRequest == null){
                throw new IllegalArgumentException("invalid request body");
            }

            List<String> illegalArguments = koalaRequest.invalidParams();

            if(CollectionUtils.isNotEmpty(illegalArguments)){
                throw new IllegalArgumentException("invalid "+ illegalArguments.toString());
            }
            out.add(koalaRequest);
        }catch (IllegalArgumentException | JSONException e){
            log.warn("decode param invalid : {} ,errorMessage={}",content,e.getMessage());
            response(ctx.channel(), KoalaResponse.error(requestId,type, EnumResponseStatus.INVALID_PARAM,e.getMessage()));
        }catch (Exception e){
            log.warn("decode exception param : {}",content,e);
            response(ctx.channel(),KoalaResponse.error(requestId,type, EnumResponseStatus.SYSTEM_EXCEPTION,e.getMessage()));
        }
    }

    private void response(Channel channel, KoalaResponse koalaResponse){
        channel.writeAndFlush(JSON.toJSONString(koalaResponse));
    }
}
