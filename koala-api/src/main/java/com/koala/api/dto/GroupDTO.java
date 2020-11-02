package com.koala.api.dto;

import lombok.Data;

/**
 * @author XiuYang
 * @date 2020/10/26
 */
@Data
public class GroupDTO {

    private String groupId;

    private String groupName;

    private String avatar;

    private String introduce;

    private String creatorId;

    private Long createTime;

}
