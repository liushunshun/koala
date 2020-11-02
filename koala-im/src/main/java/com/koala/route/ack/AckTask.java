package com.koala.route.ack;

import com.alibaba.fastjson.JSON;
import com.koala.api.dto.MessageDTO;
import com.koala.api.dto.Result;
import com.koala.route.push.MessagePushManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author XiuYang
 * @date 2020/10/23
 */
@Slf4j
public class AckTask implements Runnable{

    private MessageDTO message;

    private MessagePushManager messagePushManager;

    private Integer checkCount;

    public AckTask(MessageDTO message, MessagePushManager messagePushManager){
        this.message = message;
        this.checkCount = 0;
        this.messagePushManager = messagePushManager;
    }

    @Override
    public void run() {

        if(checkCount >= MessageAckManager.RETRY_COUNT){

            log.warn("消息超时未ack: message={}",JSON.toJSONString(message));
            MessageAckManager.unAckSet.remove(message.getMid());

            return;
        }

        if(MessageAckManager.unAckSet.contains(message.getMid())){

            log.info("重新推送：{}", JSON.toJSONString(message));

            Result result = messagePushManager.push(message);

            if(! result.isOk()){
                log.error("重推失败：message={},result={}",JSON.toJSONString(message),JSON.toJSONString(result));
            }

            this.checkCount ++;

            MessageAckManager.addTask(this);
        }else{

            log.info("消息已经ack：{}", JSON.toJSONString(message));
        }

    }
}
