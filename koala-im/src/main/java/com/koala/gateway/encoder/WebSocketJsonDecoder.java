package com.koala.gateway.encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.koala.api.enums.MessageType;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.dto.KoalaAckRequest;
import com.koala.gateway.dto.KoalaSendRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.enums.RequestType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

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
        String requestId = null;
        String type = "";
        ConnectionParam connectionParam = null;
        try{
            connectionParam = ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).get();

            JSONObject object = JSON.parseObject(content);

            type = object.getString("type");

            RequestType requestType = RequestType.getEnum(type);

            if(requestType == null){
                throw new IllegalArgumentException("type not support");
            }

            KoalaRequest koalaRequest = null;

            if(requestType == RequestType.CHAT_MSG_ACK){
                koalaRequest = KoalaAckRequest.paramParse(content);
            }else if(requestType == RequestType.CHAT_MSG_SEND){
                koalaRequest = KoalaSendRequest.paramParse(content);
            }

            if(koalaRequest == null){
                throw new IllegalArgumentException("parse koalaRequest failed");
            }

            if(koalaRequest.getRequestId() == null){
                koalaRequest.setRequestId(UUID.randomUUID().toString().replace("-",""));
            }

            requestId = koalaRequest.getRequestId();

            koalaRequest.setConnectionParam(connectionParam);
            koalaRequest.setChannel(ctx.channel());

            out.add(koalaRequest);
        }catch (IllegalArgumentException | JSONException e){
            log.warn("decode param invalid : connectionParam={},content={} ,errorMessage={}",JSON.toJSONString(connectionParam),content,e.getMessage());
            response(ctx.channel(), KoalaResponse.response(requestId,type, ResponseStatus.INVALID_PARAM,e.getMessage()));
        }catch (Exception e){
            log.warn("decode exception param : connectionParam={},content{}",JSON.toJSONString(connectionParam),content,e);
            response(ctx.channel(),KoalaResponse.response(requestId,type, ResponseStatus.SYSTEM_EXCEPTION,e.getMessage()));
        }
    }

    private void response(Channel channel, KoalaResponse koalaResponse){
        channel.writeAndFlush(koalaResponse);
    }
}
