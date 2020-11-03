package com.koala.gateway.server.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.koala.api.BizException;
import com.koala.api.dto.Result;
import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.enums.RequestType;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2020/11/02
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class HttpServerHandler extends SimpleChannelInboundHandler<KoalaRequest> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, KoalaRequest request) throws Exception {

        try{
            RequestHandler requestHandler = HttpRequestHandlerFactory.getHandler(RequestType.getEnum(request.getRequestType()));

            if(requestHandler == null){
                throw new BizException(ResponseStatus.INVALID_REQUEST_TYPE);
            }
            Result response = requestHandler.handle(request);

            response.setRequestType(request.getRequestType());
            response.setRequestId(request.getRequestId());

            log.info("HttpServerHandler.channelRead0 request={},response={}", JSON.toJSONString(request),JSON.toJSONString(response));
            ctx.channel().writeAndFlush(response);

        }catch (JSONException e){
            log.error("HttpServerHandler.channelRead0 exception request={}", JSON.toJSONString(request),e);
            ctx.channel().writeAndFlush(new Result(ResponseStatus.INVALID_PARAM,e.getMessage())).addListener(ChannelFutureListener.CLOSE);
        }catch (BizException e){
            log.info("HttpServerHandler.channelRead0 exception request={}", JSON.toJSONString(request),e);
            ctx.channel().writeAndFlush(new Result(e.getCode(),e.getMessage())).addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.warn("WebSocketServerHandler exception",cause);
        ctx.channel().writeAndFlush(new Result(ResponseStatus.SYSTEM_EXCEPTION)).addListener(
            ChannelFutureListener.CLOSE);
        ctx.close();
        return;
    }
}
