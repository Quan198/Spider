package com.liu.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.core.LoggerContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author w
 */
@Slf4j
@RestController
public class test {

    @RequestMapping("test1")
    public void test1() {
        // 看是否开启异步日志
        LoggerContext context = (LoggerContext) org.apache.logging.log4j.LogManager.getContext(false);
        log.info(context.getClass().getName());

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            // 测试 MDC 在子线程中是否能使用
            log.info("线程打印日志");
        }).start();
    }
}
