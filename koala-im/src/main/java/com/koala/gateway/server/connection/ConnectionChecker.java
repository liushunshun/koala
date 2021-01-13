//package com.koala.gateway.server.connection;
//
//import io.netty.channel.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.time.LocalDateTime;
//import java.util.Map;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.ScheduledThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author XiuYang
// * @date 2019/10/18
// */
//@Slf4j
//@Component
//public class ConnectionChecker {
//
//    @Autowired
//    private ConnectionManager connectionManager;
//
//    private static ScheduledExecutorService excutor =  new ScheduledThreadPoolExecutor(1);
//
//    private static final long INTERVAL = 1000 * 60;
//    private static final long CONNECTION_TIMEOUT = 1000 * 60 * 3;
//
//    public void check() {
//        excutor.scheduleWithFixedDelay(() -> {
//            try {
//                Map<String, Channel> channels = connectionManager.getAllChannels();
//                for (String token : channels.keySet()) {
//                    if (isTimeout(channels.get(token))) {
//                        onTimeout(token);
//                    }
//                }
//            } catch (Exception e) {
//                log.warn("ConnectionChecker.checkConnection exception", e);
//            }
//        }, 0, INTERVAL, TimeUnit.MILLISECONDS);
//    }
//
//    private static boolean isTimeout(Channel channel) {
//        if(channel == null){
//            return true;
//        }
//        LocalDateTime heartBeatTime = channel.attr(ConnectionParam.HEART_BEAT_TIME).get();
//        if(heartBeatTime != null && heartBeatTime.isBefore(LocalDateTime.now().minusMinutes(CONNECTION_TIMEOUT))){
//            return true;
//        }
//        return false;
//    }
//
//    private void onTimeout(String token) {
//        try {
//            connectionManager.close(token);
//        } catch (Exception e) {
//            log.warn("ConnectionChecker.onTimeout exception token={}",token, e);
//        }
//    }
//
//}
