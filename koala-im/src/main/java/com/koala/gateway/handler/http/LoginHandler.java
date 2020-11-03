package com.koala.gateway.handler.http;

import com.koala.api.enums.ResponseStatus;
import com.koala.gateway.dto.KoalaLoginRequest;
import com.koala.gateway.dto.KoalaRequest;
import com.koala.gateway.dto.KoalaResponse;
import com.koala.gateway.dto.LoginResult;
import com.koala.gateway.enums.RequestType;
import com.koala.gateway.server.handler.RequestHandler;
import com.koala.gateway.server.handler.HttpRequestHandlerFactory;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.UUID;

/**
 * @author XiuYang
 * @date 2020/11/02
 */
@Component
public class LoginHandler implements RequestHandler {

    @Override
    public RequestType getRequestType() {
        return RequestType.LOGIN;
    }

    @Override
    public KoalaResponse handle(KoalaRequest koalaRequest){

        KoalaLoginRequest loginRequest = (KoalaLoginRequest)koalaRequest;

        if(Objects.equals(loginRequest.getUsername(),"admin") && Objects.equals(loginRequest.getPassword(),"123")){

            LoginResult result = new LoginResult();
            result.setToken(UUID.randomUUID().toString().replace("-",""));

            KoalaResponse koalaResponse = new KoalaResponse();
            koalaResponse.setData(result);
            return koalaResponse;
        }
        return new KoalaResponse(ResponseStatus.AUTH_FAILED);
    }

    @Override
    public void afterPropertiesSet() {
        HttpRequestHandlerFactory.register(this);
    }
}
