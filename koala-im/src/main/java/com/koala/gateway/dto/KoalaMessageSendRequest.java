package com.koala.gateway.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/15
 */

@Data
public class KoalaMessageSendRequest extends KoalaRequest{

    private String sessionId;

    private String msgType;

    private String content;

    private String clientMessageId;

    @Override
    public List<String> invalidParams() {
        List<String> list = new ArrayList<>();

        if(StringUtils.isBlank(msgType)){
            list.add("msgType");
        }
        if(StringUtils.isBlank(content)){
            list.add("content");
        }
        if(StringUtils.isBlank(sessionId)){
            list.add("sessionId");
        }
        if(StringUtils.isBlank(clientMessageId)){
            list.add("clientMessageId");
        }

        return list;

    }
}
