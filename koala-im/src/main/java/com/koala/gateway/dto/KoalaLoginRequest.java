package com.koala.gateway.dto;

import com.koala.gateway.encoder.KoalaRequestParamParser;
import com.koala.gateway.enums.RequestType;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author XiuYang
 * @date 2020/11/02
 */
@Data
public class KoalaLoginRequest extends KoalaRequest {

    private String username;

    private String password;

    @Override
    public List<String> invalidParams() {
        List<String> list = Lists.newArrayList();
        if(StringUtils.isBlank(username)){
            list.add("username");
        }
        if(StringUtils.isBlank(password)){
            list.add("password");
        }
        return list;
    }
}
