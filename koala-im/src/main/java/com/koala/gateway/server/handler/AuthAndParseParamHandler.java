package com.koala.gateway.server.handler;

import com.koala.api.dto.Result;
import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.enums.RequestType;
import com.koala.utils.HttpParamParser;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

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

        log.info("执行：AuthAndParseParamHandler");
        //解析参数
        ConnectionParam connectionParam = parseParam(httpRequest);

        log.warn("HTTP_REQUEST url={},connectionParam={}",httpRequest.uri(),connectionParam);

        //鉴权
        QueryStringDecoder decoder = new QueryStringDecoder(httpRequest.uri());

        boolean authResult = Objects.equals(decoder.path(),RequestType.LOGIN.getCode()) ? true : auth(connectionParam);
        if(!authResult){
            log.warn("AuthAndParseParamHandler auth failed uri={} ,connectionParam={}",httpRequest.uri(),connectionParam);
            ctx.channel().writeAndFlush(new Result(ResponseStatus.AUTH_FAILED)).addListener(ChannelFutureListener.CLOSE);
            return;
        }
        ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).set(connectionParam);

        ctx.fireChannelRead(httpRequest.retain());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("base response handler: exception caught", cause);
        ctx.channel().writeAndFlush(new Result(ResponseStatus.SYSTEM_EXCEPTION)).addListener(ChannelFutureListener.CLOSE);
        super.exceptionCaught(ctx, cause);
    }

    private List<String> checkParam(ConnectionParam param){
        if(param == null || StringUtils.isBlank(param.getToken())){
            return Lists.newArrayList("token");
        }
        return null;
    }

    private ConnectionParam parseParam(FullHttpRequest request){
        ConnectionParam connectionParam = new ConnectionParam();
        String uri = request.uri();
        try{

            HttpParamParser httpParamParser = new HttpParamParser(request);

            String token = httpParamParser.getParam("token");

            connectionParam.setToken(token);
            connectionParam.setProtocol(Objects.equals(WebSocketChannelInitializer.WS_URI,uri) ? ConnectionParam.WS : ConnectionParam.HTTP);
        }catch (Exception e){
            log.error("AuthAndParseParamHandler parseParam exception uri={},connectionParam={}",uri,connectionParam);
        }
        return connectionParam;
    }

    private boolean auth(ConnectionParam param){

        if(param == null || StringUtils.isBlank(param.getToken())){
            return false;
        }
        return true;
    }
}