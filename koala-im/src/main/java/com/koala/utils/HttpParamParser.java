package com.koala.utils;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @author XiuYang
 * @date 2019/10/18
 */
@Slf4j
public class HttpParamParser {

    private FullHttpRequest request;
    private QueryStringDecoder queryStringDecoder;
    private InterfaceHttpPostRequestDecoder httpPostRequestDecoder;


    public HttpParamParser(FullHttpRequest request){
        this.request = request;

        HttpMethod httpMethod = request.method();
        if (HttpMethod.POST.equals(httpMethod)) {
            HttpDataFactory httpDataFactory = new DefaultHttpDataFactory(false);
            this.httpPostRequestDecoder = new HttpPostRequestDecoder(httpDataFactory, request);
        }

        String uri = request.uri();
        this.queryStringDecoder = new QueryStringDecoder(uri);

    }

    /**
     * 从FullHttpRequest中获取指定的参数值
     *
     * @param paramName        参数名
     * @return 参数值
     */
    public String getParam(String paramName) {
        try {
            //  获取参数值
            //  1、如果是POST的请求，首先到从POST参数中获取
            String value = null;
            HttpMethod method = request.method();
            if (HttpMethod.POST.equals(method)) {
                value = getPostParam(paramName);
            }

            //  2、如果找不到参数值，那么尝试到请求头中获取
            if (value == null) {
                value = getParamFromHead(paramName);
            }

            //  3、如果还获取不到，那么尝试从请求QUERY部分中获取
            //  万一POST请求的URL上带东西了呢
            if (value == null) {
                value = getParamFromQuery(paramName);
            }

            //  4、返回最终结果
            return value;
        } catch (Throwable throwable) {
            log.error("HttpParamParser: get param exception, paramName: {}", paramName, throwable);
            return null;
        }
    }

    /**
     * 从FullHttpRequest中获取指定的参数值，并解析为Integer类型
     *
     * @param paramName        参数名
     * @return 参数值
     */
    public Integer getParamInteger(String paramName) {
        try {
            //  1、从WebImHttpRequest中获取指定的参数值
            String stringValue = getParam(paramName);

            //  2、如果获取参数值失败，那么返回null
            if (StringUtils.isBlank(stringValue)) {
                return null;
            }

            //  3、将参数值解析为Integer类型
            return Integer.parseInt(stringValue);
        } catch (Throwable throwable) {
            log.error("HttpParamParser: get param and cast int exception, paramName: {}", paramName, throwable);
            return null;
        }
    }

    /**
     * 从FullHttpRequest中获取指定的参数值，并解析为Long类型
     *
     * @param paramName        参数名
     * @return 参数值
     */
    public Long getParamLong(String paramName) {
        try {
            //  1、从WebImHttpRequest中获取指定的参数值
            String stringValue = getParam(paramName);

            //  2、如果获取参数值失败，那么返回null
            if (StringUtils.isBlank(stringValue)) {
                return null;
            }

            //  3、将参数值解析为Long类型
            return Long.parseLong(stringValue);
        } catch (Throwable throwable) {
            log.error("HttpParamParser: get param and cast long exception, paramName: {}", paramName, throwable);
            return null;
        }
    }

    /**
     * 获取post请求中的参数值
     *
     * @param paramName        参数名
     * @return post请求中参数的值
     */
    public String getPostParam(String paramName) {
        try {
            //  获取post请求中的参数值
            //  0、校验参数
            if (StringUtils.isBlank(paramName)) {
                throw new IllegalArgumentException("paramName is null");
            }
            //  1、非POST请求不需要从这里查询参数
            HttpMethod httpMethod = request.getMethod();
            if (!HttpMethod.POST.equals(httpMethod)) {
                return null;
            }

            //  3、尝试获取参数
            InterfaceHttpData httpData = httpPostRequestDecoder.getBodyHttpData(paramName);
            if (null == httpData) {
                //  如果没有找到HttpData，那么返回null
                return null;
            }

            //  4、接着校验HttpData的类型
            InterfaceHttpData.HttpDataType httpDataType = httpData.getHttpDataType();
            if (!InterfaceHttpData.HttpDataType.Attribute.equals(httpDataType)) {
                //  如果获取到的HttpData类型不是attribute，那么返回null
                return null;
            }

            //  5、尝试获取参数值
            return Attribute.class.cast(httpData).getValue();
        } catch (Throwable throwable) {
            //  获取参数值异常，记录日志
            log.error("HttpParamParser: get post param exception, paramName: {}", paramName, throwable);
            return null;
        }
    }

    /**
     * 从请求头中获取参数值
     *
     * @param paramName        参数名
     * @return 请求头中的参数值
     */
    public String getParamFromHead(String paramName) {
        try {
            //  获取请求头中的参数值
            //  0、校验参数
            if (StringUtils.isBlank(paramName)) {
                throw new IllegalArgumentException("paramName is null");
            }
            //  1、获取请求头
            HttpHeaders headers = request.headers();
            if (headers == null || headers.isEmpty()) {
                //  如果请求头是空的，那么返回null
                return null;
            }
            //  2、从请求头中获取参数值
            return headers.get(paramName);
        } catch (Throwable throwable) {
            log.error("HttpParamParser: get param from head exception, paramName: {}", paramName, throwable);
            return null;
        }
    }

    /**
     * 从请求的QUERY部分中获取参数值
     *
     * @param paramName        参数名
     * @return 请求QUERY部分中的参数值
     */
    public String getParamFromQuery(String paramName) {
        try {
            //  获取请求QUERY部分的参数值
            //  0、校验参数
            if (StringUtils.isBlank(paramName)) {
                throw new IllegalArgumentException("paramName is null");
            }

            if (request == null) {
                throw new IllegalArgumentException("webImHttpRequest is null");
            }

            //  1、尝试查找参数值列表
            List<String> valueList = getParamListFromQuery(paramName);
            if (CollectionUtils.isEmpty(valueList)) {
                return null;
            }

            //  2、我们获取第一个
            return valueList.get(0);
        } catch (Throwable throwable) {
            log.error("HttpParamParser: get param from query exception, paramName: {}", paramName, throwable);
            return null;
        }
    }

    /**
     * 从请求的QUERY部分中获取参数值列表
     *
     * @param paramName        参数名
     * @return 请求QUERY部分中的参数值
     */
    public List<String> getParamListFromQuery(String paramName) {
        try {
            //  获取请求QUERY部分的参数值列表
            //  0、校验参数
            if (StringUtils.isBlank(paramName)) {
                throw new IllegalArgumentException("paramName is null");
            }
            //  2、从QueryStringDecoder中获取参数的Map
            Map<String, List<String>> parameterMap = queryStringDecoder.parameters();
            if (MapUtils.isEmpty(parameterMap)) {
                return null;
            }
            //  3、尝试查找参数值列表
            return parameterMap.get(paramName);
        } catch (Throwable throwable) {
            log.error("HttpParamParser: get param list from query exception, paramName: {}", paramName, throwable);
            return null;
        }
    }

}