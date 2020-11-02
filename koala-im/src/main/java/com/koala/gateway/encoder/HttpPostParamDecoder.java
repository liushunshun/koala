package com.koala.gateway.encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.koala.api.BizException;
import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.connection.ConnectionParam;
import com.koala.gateway.dto.KoalaEmptyParamRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.enums.RequestType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.List;
import java.util.UUID;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Slf4j
@Component
public class HttpPostParamDecoder extends MessageToMessageDecoder<FullHttpRequest> {

    @Override
    protected void decode(ChannelHandlerContext ctx, FullHttpRequest request, List<Object> out) throws Exception {

        KoalaRequest koalaRequest = null;

        ConnectionParam connectionParam = null;
        String content = null;
        try {
            connectionParam = ctx.channel().attr(ConnectionParam.CHANNEL_PARAM).get();

            content = request.content().toString(Charset.forName("UTF-8"));

            if (StringUtils.isNotBlank(content)) {

                RequestType requestType = RequestType.getEnum(request.uri());

                if (requestType == null) {
                    throw new BizException(ResponseStatus.INVALID_REQUEST_TYPE);
                }

                koalaRequest = KoalaRequestParamParser.parseParam(requestType, content);
                koalaRequest.setRequestType(requestType.getCode());
            }
            if (koalaRequest == null) {
                koalaRequest = new KoalaEmptyParamRequest();
            }

            if (koalaRequest.getRequestId() == null) {
                koalaRequest.setRequestId(UUID.randomUUID().toString().replace("-", ""));
            }

            koalaRequest.setConnectionParam(connectionParam);
            koalaRequest.setChannel(ctx.channel());

            out.add(koalaRequest);

        } catch (IllegalArgumentException | JSONException e) {
            log.warn("decode param invalid : connectionParam={},content={} ,errorMessage={}", JSON.toJSONString(connectionParam), content, e.getMessage());
            response(ctx.channel(), KoalaResponse.response(koalaRequest.getRequestId(), koalaRequest.getRequestType(), ResponseStatus.INVALID_PARAM, e.getMessage()));
        } catch (Exception e) {
            log.warn("decode exception param : connectionParam={},content{}", JSON.toJSONString(connectionParam), content, e);
            response(ctx.channel(), KoalaResponse.response(koalaRequest.getRequestId(), koalaRequest.getRequestType(), ResponseStatus.SYSTEM_EXCEPTION, e.getMessage()));
        }
    }

    private void response(Channel channel, KoalaResponse koalaResponse) {
        channel.writeAndFlush(koalaResponse);
    }
}
