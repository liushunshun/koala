package com.koala.gateway.handler;

import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.KoalaRequest;

/**
 * @author XiuYang
 * @date 2019/10/16
 */

public interface KoalaHandler {

    KoalaResponse process(KoalaRequest koalaRequest);
}
