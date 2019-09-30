package com.koala.utils;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author XiuYang
 * @date 2019/09/30
 */

public class UUIDGenerator {
    private static AtomicLong opaque = new AtomicLong();
    public static final long getNextOpaque() {
        return opaque.getAndIncrement();
    }
}
