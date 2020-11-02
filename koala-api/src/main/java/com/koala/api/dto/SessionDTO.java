package com.koala.api.dto;

import lombok.Data;

/**
 * @author XiuYang
 * @date 2020/10/26
 */
@Data
public class SessionDTO {

    private String sid;

    /**
     * @see com.koala.api.enums.SessionType
     */
    private Integer sessionType;

    private Long createTime;

}
