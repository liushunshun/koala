package com.koala.core.message.dto;

import lombok.Data;

import java.util.List;

/**
 * @author XiuYang
 * @date 2019/10/21
 */
@Data
public class MessagePushParam extends MessageDTO{

    /**
     * 接收人ID
     */
    private String receiverId;

    /**
     * 接收人所在端
     */
    private List<String> receiverApps;

    /**
     * 失败后重试次数
     */
    private Integer retryCount = 3;

    /**
     * 是否保存推送记录
     */
    private boolean saveLog = false;

}
