package com.koala.gateway.starter;

import com.koala.gateway.server.NettyServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author XiuYang
 * @date 2019/10/14
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.koala.gateway")
public class GatewayStarter implements CommandLineRunner {

    @Autowired
    private NettyServer nettyServer;

    public static void main(String[] args) {
        SpringApplication.run(GatewayStarter.class, args);
    }

    @Override
    public void run(String... args) {
        try{
            nettyServer.start(Integer.valueOf(args[0]));
        }catch (Exception e){
            log.error("netty server start error ",e);
        }finally {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> nettyServer.shutdown()));
        }
    }
}

