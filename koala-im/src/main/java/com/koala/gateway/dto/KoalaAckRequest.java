package com.koala.gateway.dto;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/15
 */

@Data
public class KoalaAckRequest extends KoalaRequest{

    private String sessionId;

    private List<String> messageIds;

    @Override
    public List<String> invalidParams() {
        List<String> list = new ArrayList<>();

        if(CollectionUtils.isEmpty(messageIds)){
            list.add("sessionId");
        }
        return list;

    }

    public static KoalaRequest paramParse(String content) {
        return JSON.parseObject(content,KoalaAckRequest.class);
    }
}
