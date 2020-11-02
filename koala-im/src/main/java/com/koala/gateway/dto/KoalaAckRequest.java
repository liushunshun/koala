package com.koala.gateway.dto;

import com.koala.gateway.encoder.KoalaRequestParamParser;
import com.koala.gateway.enums.RequestType;
import lombok.Data;
import org.apache.commons.collections4.CollectionUtils;

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

}
