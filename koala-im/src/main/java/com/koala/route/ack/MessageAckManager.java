package com.koala.route.ack;

import com.koala.api.dto.MessageDTO;
import com.koala.route.push.MessagePushManager;
import com.koala.utils.AsyncThreadPool;
import io.netty.util.HashedWheelTimer;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.TimeUnit;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Slf4j
@Component
public class MessageAckManager {

    /**
     * 消息重试次数
     */
    public static final int RETRY_COUNT = 3;

    /**
     * 消息重试间隔时间
     */
    public static final int RETRY_TIME_INTERVAL_SEC = 10;

    public static final Set<String> unAckSet = new ConcurrentSkipListSet<>();

    private static final HashedWheelTimer wheelTimer = new HashedWheelTimer();

    @Autowired
    private MessagePushManager messagePushManager;

    static {
        monitorLog();
    }

    private static void monitorLog() {
        TimerTask timerTask = timeout -> {

            log.info("当前时间未ack消息数量："+unAckSet.size());
            monitorLog();
        };

        wheelTimer.newTimeout(timerTask ,10, TimeUnit.SECONDS);
    }

    public void checkAck(MessageDTO message){
        unAckSet.add(message.getMid());
        addTask(new AckTask(message,messagePushManager));
    }

    public static void addTask(Runnable runnable){
        wheelTimer.newTimeout(timeout -> AsyncThreadPool.EXECUTOR.submit(runnable) ,RETRY_TIME_INTERVAL_SEC, TimeUnit.SECONDS);
    }
}
