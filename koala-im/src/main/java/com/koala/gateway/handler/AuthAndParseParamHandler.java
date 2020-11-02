package com.koala.gateway.handler;

import com.alibaba.fastjson.JSON;
import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.enums.RequestType;
import com.koala.utils.HttpParamParser;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * 参数解析及鉴权
 *
 * @author XiuYang
 * @date 2019/10/14
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class AuthAndParseParamHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest httpRequest) throws Exception {
        //解析参数
        ConnectionParam connectionParam = parseParam(httpRequest);

        log.warn("HTTP_REQUEST url={},connectionParam={}",httpRequest.uri(),connectionParam);

        //参数校验
        List<String> invalidParams = checkParam(connectionParam);

        if(CollectionUtils.isNotEmpty(invalidParams)){
            log.warn("AuthAndParseParamHandler checkParam failed uri={} ,connectionParam={},invalidParams={}",httpRequest.uri(),connectionParam,invalidParams);
            ctx.channel().writeAndFlush(JSON.toJSONString(KoalaResponse.response("", RequestType.CONNECTION.getCode(), ResponseStatus.INVALID_PARAM, JSON.toJSONString(invalidParams)))).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        //鉴权
        boolean authResult = auth(connectionParam);
        if(!authResult){
            log.warn("AuthAndParseParamHandler auth failed uri={} ,connectionParam={}",httpRequest.uri(),connectionParam);
            ctx.channel().writeAndFlush(KoalaResponse.response("", RequestType.CONNECTION.getCode(), ResponseStatus.AUTH_FAILED)).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).set(connectionParam);

        ctx.fireChannelRead(httpRequest.retain());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("base response handler: exception caught", cause);
        ctx.channel().writeAndFlush(KoalaResponse.response("", RequestType.CONNECTION.getCode(), ResponseStatus.SYSTEM_EXCEPTION)).addListener(ChannelFutureListener.CLOSE);
        super.exceptionCaught(ctx, cause);
    }

    private List<String> checkParam(ConnectionParam param){
        if(param == null || StringUtils.isBlank(param.getUserId())){
            return Lists.newArrayList("userId");
        }
        return null;
    }

    private ConnectionParam parseParam(FullHttpRequest request){
        ConnectionParam connectionParam = new ConnectionParam();
        String uri = request.uri();
        try{

            HttpParamParser httpParamParser = new HttpParamParser(request);

            String userId = httpParamParser.getParam("userId");

            connectionParam.setUserId(userId);

        }catch (Exception e){
            log.error("AuthAndParseParamHandler parseParam exception uri={},connectionParam={}",uri,connectionParam);
        }
        return connectionParam;
    }

    private boolean auth(ConnectionParam param){

        if(param == null || StringUtils.isBlank(param.getUserId())){
            return false;
        }
        return true;
    }
}