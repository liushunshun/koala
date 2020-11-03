package com.koala.gateway.server.handler;

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
public interface RequestHandler extends InitializingBean{

    /**
     * 支持的请求类型
     * @return
     */
    RequestType getRequestType();

    /**
     * 请求处理
     * @param koalaRequest
     * @return
     * @throws BizException
     */
    KoalaResponse handle(KoalaRequest koalaRequest)throws BizException;

}
