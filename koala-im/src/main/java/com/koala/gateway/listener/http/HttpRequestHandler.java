package com.koala.gateway.listener.http;

import com.koala.api.BizException;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.enums.RequestType;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author XiuYang
 * @date 2020/11/02
 */
@Component
public interface HttpRequestHandler extends InitializingBean{

    RequestType getRequestType();

    KoalaResponse handle(KoalaRequest koalaRequest)throws BizException;

}
