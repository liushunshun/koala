package com.koala.gateway.dto;

import com.koala.api.dto.Result;
import com.koala.api.enums.ResponseStatus;
import lombok.Builder;
import lombok.Data;

/**
 * @author XiuYang
 * @date 2019/10/16
 */
@Data
public class KoalaResponse extends Result{

    /**
     * 请求ID
     */
    private String requestId;

    /**
     * 协议类型，心跳、发送聊天消息、数据请求、消息推送
     */
    private String type;

    public KoalaResponse() {
    }

    public KoalaResponse(String requestId,String type) {
        this.requestId = requestId;
        this.type = type;
    }

    public KoalaResponse(ResponseStatus status) {
        if(ResponseStatus.OK != ResponseStatus.getEnum(status.getCode())){
            setOk(false);
        }
        setErrorCode(status.getCode());
        setErrorMessage(status.getDescription());
    }
    public KoalaResponse(Integer errorCode,String errorMsg) {
        if(ResponseStatus.OK != ResponseStatus.getEnum(errorCode)){
            setOk(false);
        }
        setErrorCode(errorCode);
        setErrorMessage(errorMsg);
    }

    public KoalaResponse(Result result) {
        setOk(result.isOk());
        setErrorCode(result.getErrorCode());
        setErrorMessage(result.getErrorMessage());
        setData(result.getData());
    }

    public static KoalaResponse response(String requestId, String type, ResponseStatus responseStatus){

        KoalaResponse response =  new KoalaResponse(requestId,type);
        response.response(responseStatus);

        return response;
    }

    public static KoalaResponse response(String requestId, String type, ResponseStatus responseStatus, String errorMessage){

        KoalaResponse response =  new KoalaResponse(requestId,type);
        response.response(responseStatus,errorMessage);

        return response;
    }

    public static KoalaResponse response(String requestId, String type, Integer errorCode, String errorMessage){

        KoalaResponse response =  new KoalaResponse(requestId,type);
        response.response(errorCode,errorMessage);

        return response;
    }

}
