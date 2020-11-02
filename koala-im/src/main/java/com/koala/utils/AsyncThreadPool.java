package com.koala.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Slf4j
public class AsyncThreadPool {

    public static final ThreadPoolExecutor EXECUTOR;

    static {
        EXECUTOR = new ThreadPoolExecutor(32,64,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>(1024),new NamedThreadFactory("koala-share"),new ThreadPoolExecutor.AbortPolicy());
    }

    public static class NamedThreadFactory  implements ThreadFactory {

        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private String namePrefix;
        private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

        public NamedThreadFactory(String  threadNamePrefix){
            this(threadNamePrefix,null);
        }

        public NamedThreadFactory(String threadNamePrefix, Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            if (StringUtils.isBlank(threadNamePrefix)) {
                throw new RuntimeException("threadNamePrefix must not be null");
            }
            if (uncaughtExceptionHandler!=null){
                this.uncaughtExceptionHandler=uncaughtExceptionHandler;
            }
            SecurityManager s = System.getSecurityManager();
            group = Objects.nonNull(s) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = threadNamePrefix;
        }

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
            if (t.isDaemon()) { t.setDaemon(false); }
            if (t.getPriority() != Thread.NORM_PRIORITY) {
                t.setPriority(Thread.NORM_PRIORITY);
            }
            if (uncaughtExceptionHandler!=null){
                t.setUncaughtExceptionHandler(uncaughtExceptionHandler);
            }

            return t;
        }
    }
}
