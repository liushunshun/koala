package com.koala.gateway.tcp;

import com.koala.gateway.tcp.ByteBufferWrapper;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public interface Protocol {

    Object decode(ByteBufferWrapper wrapper, int originPos) throws Exception;

}
