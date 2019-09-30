package com.koala.gateway.protocol;

import com.koala.gateway.encoder.ByteBufferWrapper;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public interface Protocol {

    int getHeadLength();

    Object decode(ByteBufferWrapper wrapper, int originPos) throws Exception;

}
