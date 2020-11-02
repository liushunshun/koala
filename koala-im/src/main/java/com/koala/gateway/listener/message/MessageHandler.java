package com.koala.gateway.listener.message;

import com.koala.api.BizException;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;

/**
 * @author XiuYang
 * @date 2019/10/21
 */

public interface MessageHandler {

    KoalaResponse receive(KoalaRequest koalaRequest)throws BizException;
}
