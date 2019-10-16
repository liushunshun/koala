package com.koala.gateway.dto;

import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/15
 */

@Data
public class KoalaMessageAckRequest extends KoalaRequest{

    private String sessionId;

    private List<String> messageIds;

    private String clientMessageId;

    @Override
    public List<String> invalidParams() {
        List<String> list = new ArrayList<>();

        if(CollectionUtils.isEmpty(messageIds)){
            list.add("messageIds");
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
