package com.koala.gateway.encoder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.koala.api.dto.Result;
import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.server.connection.ConnectionParam;
import com.koala.gateway.dto.KoalaEmptyParamRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.enums.RequestType;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
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

                QueryStringDecoder decoder = new QueryStringDecoder(request.uri());
                RequestType requestType = RequestType.getEnum(decoder.path());

                if (requestType == null) {
                    out.add(request.retain());
                    return;
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
            response(ctx.channel(), new Result(ResponseStatus.INVALID_PARAM));
        } catch (Exception e) {
            log.warn("decode exception param : connectionParam={},content{}", JSON.toJSONString(connectionParam), content, e);
            response(ctx.channel(), new Result(ResponseStatus.SYSTEM_EXCEPTION));
        }
    }

    private void response(Channel channel, Result result) {
        channel.writeAndFlush(result);
    }
}
