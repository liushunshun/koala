package com.koala.api.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
public class TextMessageDTO extends MessageDTO{

    @Getter
    private String msgType = "text";

    @Getter @Setter
    private String text;

    @Override
    public Map<String, Object> getBody() {
        return new HashMap<String,Object>(1){{
            put("text",text);
        }};
    }

    @Override
    public void setBody(String body) {
        JSONObject jsonObject = JSON.parseObject(body);
        this.text = jsonObject.getString("text");
    }
}
