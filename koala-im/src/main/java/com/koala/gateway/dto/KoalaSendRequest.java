package com.koala.gateway.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.koala.api.enums.MessageType;
import com.koala.gateway.encoder.KoalaRequestParamParser;
import com.koala.gateway.enums.RequestType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/15
 */

@Data
public abstract class KoalaSendRequest extends KoalaRequest{

    private String sessionId;

    private String receiverId;

    private String msgType;

    private String sendApp;

    private String senderId;

    @Override
    public List<String> invalidParams() {
        List<String> list = new ArrayList<>();

        if(StringUtils.isBlank(msgType)){
            list.add("msgType");
        }
        if(StringUtils.isBlank(sessionId) && StringUtils.isBlank(receiverId)){
            list.add("sessionId or receiverId");
        }
        return list;
    }

}
