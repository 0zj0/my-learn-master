package com.example.testScheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangjie
 * @date 2019/11/22 15:44
 */
@Service
public class ScheduledTaskServcie {

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime(){
        System.out.println("reportCurrentTime:每隔5秒执行一次 " + new Date().toString());
    }

    @Scheduled(cron = "0/5 * * * * ? ")
    public void fixTimeExecution(){
        System.out.println("fixTimeExecution:每隔5秒执行一次 " + new Date().toString());
    }

}
