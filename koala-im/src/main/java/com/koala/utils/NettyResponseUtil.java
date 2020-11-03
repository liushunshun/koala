package com.koala.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.*;

/**
 * @author XiuYang
 * @date 2020/11/03
 */

public class NettyResponseUtil {

    public static HttpResponse httpResponseJson(String body) {
        return create(HttpResponseStatus.OK, body);
    }

    private static HttpResponse create(HttpResponseStatus status, String body) {
        HttpResponse response;

        if (body != null && body.length() > 0) {
            ByteBuf buffer = Unpooled.wrappedBuffer(body.getBytes());
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status, buffer);
        } else {
            response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, status);
        }
        response.headers().set(HttpHeaderNames.CONTENT_TYPE, "application/json");
        return response;
    }

}
