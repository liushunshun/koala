package com.koala.gateway.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.context.annotation.Import;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/15
 */

@Data
@Import(value = KoalaMessageSendRequest.class)
public class KoalaMessageSendRequest extends KoalaRequest{

    private String sessionId;

    private String msgType;

    private String content;

    private String senderId;

    private String clientMessageId;

    @Override
    public List<String> invalidParams() {

        if(StringUtils.isBlank(msgType)){
            return Lists.newArrayList("sessionId");
        }else{
            return null;
        }

    }
}
