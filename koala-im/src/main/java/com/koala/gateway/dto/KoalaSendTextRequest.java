package com.koala.gateway.dto;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/15
 */

@Data
public class KoalaSendTextRequest extends KoalaSendRequest{

    private String text;

    @Override
    public List<String> invalidParams() {


        List<String> list = super.invalidParams();

        if(StringUtils.isBlank(text)){
            list.add("text");
        }
        return list;
    }

}
