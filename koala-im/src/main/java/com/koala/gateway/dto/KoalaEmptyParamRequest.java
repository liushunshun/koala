package com.koala.gateway.dto;

import lombok.Data;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/15
 */

@Data
public class KoalaEmptyParamRequest extends KoalaRequest{

    @Override
    public List<String> invalidParams() {
        return null;
    }

}
