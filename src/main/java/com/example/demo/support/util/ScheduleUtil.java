package com.example.demo.support.util;


import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

//@Configuration
public class ScheduleUtil {


    @Scheduled(cron = "0 0/1 * * * *")
    public void test(){
        LocalDateTime localDate = LocalDateTime.now();
        System.out.println("指向时间："+localDate);
    }

}
