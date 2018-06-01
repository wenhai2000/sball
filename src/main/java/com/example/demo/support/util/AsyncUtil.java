package com.example.demo.support.util;


import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class AsyncUtil {

    @Async
    public void testA(){
        try {
            System.out.println("异步开始执行："+ LocalDateTime.now());
            Thread.sleep(3000);
            System.out.println("异步结束执行："+ LocalDateTime.now());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
