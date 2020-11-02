package com.koala.api.dto;

import lombok.Data;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Data
public class MessageSendResult {

    private String mid;

    public MessageSendResult(String mid){
        this.mid = mid;
    }
}
