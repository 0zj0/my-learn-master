package com.example.utils;

import com.example.lock.ILock;
import com.example.redis.RedisHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhangjie
 * @date 2020/11/2 18:19
 */
@Service
public class RedisLock implements ILock {

    @Autowired
    private RedisHelper redisHelper;

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    @Override
    public boolean tryLock(String key, String saltValue, long maxWaitSeconds)  {
        long sleepInterSeconds = 0L;
        long expire = maxWaitSeconds == 0 ? MAX_HOLD_SECONDS : maxWaitSeconds;
        boolean ret = redisHelper.setIfAbSent(key, saltValue, expire);
        //不管有多少个线程竞争,最终只会返回给一个为true
        while (!ret && maxWaitSeconds > 0) {
            ret = redisHelper.setIfAbSent(key, saltValue, expire);
            if (!ret && maxWaitSeconds != sleepInterSeconds) {
                try {
                    Thread.sleep(1000L);
                }catch (Exception e){
                    //不处理
                }
                sleepInterSeconds ++;
            }
        }
        /*
        //如果失败直接抛出异常,以免业务继续执行
        if (!ret) {
            throw new Exception("获取锁[" + key + "," + saltValue + "]失败");
        }*/
        return ret;

    }

    @Override
    public boolean unlock(String key, String saltValue) {
        Object dbSaltObj = redisHelper.get(key);
        //根本就没加过锁
        if (dbSaltObj == null) {
            return true;
        }
        String dbSaltValue = (String) dbSaltObj;
        //不是同一个请求设的锁
        if (!dbSaltValue.equals(saltValue)) {
            return false;
        } else {
            redisHelper.del(key);
            return true;
        }
    }

}
