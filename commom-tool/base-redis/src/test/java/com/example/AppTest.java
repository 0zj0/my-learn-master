package com.example;

import com.example.lock.ILock;
import com.example.redis.RedisHelper;
import com.example.utils.RedissLockUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * Unit test for simple App.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTest 
{
    @Resource
    private RedisHelper redisHelper;

    private final static String REDIS_PREFIX = "redis:test:";

    @Test
    public void test(){
        String key = REDIS_PREFIX + "1";
        redisHelper.incrby(key,3);
        System.out.println("redis + 3");
        System.out.println(redisHelper.get(key));
    }

    @Test
    public void testLock() throws InterruptedException {
        String lockPre = REDIS_PREFIX + "lock:%s";
        System.out.println("*************************锁测试**********************");

        for(int i = 0 ; i < 4 ; i++){
            int n = 1;
            System.out.println("n:"+n+",i:"+i+",time:"+long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S"));
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("前:thread:"+Thread.currentThread().getName()+",n:"+n+",time:"+long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S"));
                    RLock lock = RedissLockUtil.lock(String.format(lockPre,n), 1);
                    try {
                        System.out.println("进入线程:"+Thread.currentThread().getName());
                        Thread.sleep(3000);
                        System.out.println("中:thread:"+Thread.currentThread().getName()+",n:"+n+",time:"+long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S"));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        //若锁已超时释放，已报异常
                        RedissLockUtil.unlock(lock);
                        System.out.println("后:thread:"+Thread.currentThread().getName()+",n:"+n+",time:"+long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S"));
                    }
                }
            },"Thread:"+i).start();
        }
        int m = 1;
        while (m > 0){
            Thread.sleep(1000);
            m ++;
        }
        System.out.println(m);
    }

    private static String long2Str(Long ctime, String format) {
        return new SimpleDateFormat(format).format(ctime);
    }

    @Resource
    private ILock redisLock;

    @Test
    public void redisLockTest(){
        String key = "lock:test:wuId:3";
        System.out.println(long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S")+" 加锁 "+
                redisLock.tryLock(key,key,5L));
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S")+" 释放锁 "+
                redisLock.unlock(key,key));
        System.out.println(long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S")+" 加锁 "+
                redisLock.tryLock(key,key,5L));
        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S")+" 加锁 "+
                redisLock.tryLock(key,key,5L));
        System.out.println(long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S")+" 释放锁 "+
                redisLock.unlock(key,key));
        System.out.println(long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S")+" 释放锁 "+
                redisLock.unlock(key,key));
        System.out.println(long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S")+" 加锁 "+
                redisLock.tryLock(key,key,5L));
        boolean r = redisLock.tryLock(key,key,5L);
        System.out.println(long2Str(System.currentTimeMillis(),"yyyy-MM-dd HH:mm:ss:S")+" 加锁 "+ r);
    }

}
