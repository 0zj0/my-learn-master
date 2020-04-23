package com.example.demo.lock;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhangjie
 * @date 2020/4/23 13:51
 */
public class LockTest {

    ZookeeperLock zookeeperLock;

    @Before
    public void init(){
        zookeeperLock = new ZookeeperLock();
        System.out.println("初始化链接zk 成功");
    }

    @Test
    public void getLockTest() throws InterruptedException {
        Lock lock = zookeeperLock.lock("lock:zj", 10 * 1000);
        System.out.println("成功获取锁");
        Thread.sleep(Long.MAX_VALUE);
        assert lock != null;
    }

    @Test
    public void run() throws IOException, InterruptedException {
        File file = new File("d:/test.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for(int i = 0; i < 100 ; i++){
            executor.submit(()->{
                Lock lock = zookeeperLock.lock(file.getPath(), 60 * 1000);
                try {
                    System.out.println("当前节点:"+lock.getPath());
                    String firstLine = Files.lines(file.toPath()).findFirst().orElse("0");
                    int count = Integer.parseInt(firstLine);
                    count ++;
                    Files.write(file.toPath(),String.valueOf(count).getBytes());
                    System.out.println(lock.getPath() + " 写入值："+ count);
                    Thread.sleep(1000L);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    zookeeperLock.unlock(lock);
                }
            });
        }
        executor.shutdown();
        executor.awaitTermination(1000, TimeUnit.SECONDS);
        String firstLine = Files.lines(file.toPath()).findFirst().orElse("0");
        System.out.println(firstLine);
    }

}
