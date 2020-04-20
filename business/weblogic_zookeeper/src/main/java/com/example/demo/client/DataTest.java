package com.example.demo.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 张杰
 * @date 2020/4/18 23:27
 */
public class DataTest {

    ZooKeeper zooKeeper;

    /**
     * 初始连接：
     * 连接参数说明：
     * String connectString： 连接串，包括ip+端口 ,集群模式下用逗号隔开 192.168.0.149:2181,192.168.0.150:2181
     * int sessionTimeout： 会话超时时间，该值不能超过服务端所设置的minSessionTimeout(默认2s) 和maxSessionTimeout(默认60s)
     * Watcher watcher： 会话监听器，服务端事件将会触该监听
     * boolean canBeReadOnly: 该连接是否为只读的
     * HostProvider aHostProvider： 服务端地址提供者，指示客户端如何选择某个服务来调用，默认采用StaticHostProvider实
     */
    @Before
    public void init() throws IOException {
        String conn = "139.9.222.86:2181";
        zooKeeper = new ZooKeeper(conn, 40000, event ->{
            System.out.println(event.getPath());
            System.out.println(event);
        });
    }

    /**
     * 获取节点信息（无监听）
     * @throws KeeperException
     * @throws InterruptedException
     */
    @Test
    public void getData1() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData("/zj", false, null);
        System.out.println(new String(data));
    }

    /**
     * 获取节点信息（添加监听一次性)（客户端监听）
     */
    @Test
    public void getData2() throws KeeperException, InterruptedException {
        byte[] data = zooKeeper.getData("/zj", true, null);
        System.out.println(new String(data));
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * 获取节点信息（添加监听一次性)（自定义监听） 实现持久监听
     */
    @Test
    public void getData3() throws KeeperException, InterruptedException {
        Stat stat = new Stat();
        byte[] data = zooKeeper.getData("/zj", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    byte[] data1 = zooKeeper.getData(watchedEvent.getPath(), this, stat);
                    System.out.println("xxxx:"+watchedEvent);
                    System.out.println("xxxx:"+new String(data1));
                    System.out.println("xxxx:"+stat);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, stat);
        System.out.println(new String(data));
        System.out.println(stat);
        Thread.sleep(Long.MAX_VALUE);
    }

    /**
     * DataCallback cb： 返回结果：
     *      int rc： 状态码
     *      String path：节点路径
     *      Object ctx：  == 外面的 Object ctx
     *      byte data[]： 节点数据
     *      Stat stat： stat熟悉
     * Object ctx
     */
    @Test
    public void getData4() throws KeeperException, InterruptedException {
        zooKeeper.getData("/zj", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int i, String s, Object o, byte[] bytes, Stat stat) {
                System.out.println(i);
                System.out.println(s);
                System.out.println(o.toString());
                System.out.println(new String(bytes));
                System.out.println(stat);
            }
        },"");
        Thread.sleep(Long.MAX_VALUE);
    }


        /**
         * 获取子节点信息(无监控)
         * getChildren(String path, boolean watch) watch = true 时，监控到zookeeper连接构造函数的监控中
         */
    @Test
    public void getChild1() throws KeeperException, InterruptedException {
        List<String> childrenList = zooKeeper.getChildren("/zj", false);
        childrenList.forEach(System.out::println);
    }

    /**
     * 持久监听 子节点信息
     */
    @Test
    public void getChild2() throws KeeperException, InterruptedException {
        List<String> childrenList = zooKeeper.getChildren("/zj", new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                try {
                    List<String> children2 = zooKeeper.getChildren(watchedEvent.getPath(), this);
                    children2.forEach(System.out::println);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        childrenList.forEach(System.out::println);
        Thread.sleep(Long.MAX_VALUE);
    }

    @Test
    public void createNode() throws KeeperException, InterruptedException {
        List<ACL> acls = new ArrayList<>();
        //权限位：
        int perm = ZooDefs.Perms.ADMIN | ZooDefs.Perms.READ; //cdwra
        ACL acl1 = new ACL(perm,new Id("world","anyone"));
        ACL acl2 = new ACL(perm, new Id("ip", "192.168.0.149"));
        ACL acl3 = new ACL(perm, new Id("ip", "127.0.0.1"));
        acls.add(acl1);
        acls.add(acl2);
        acls.add(acl3);
        zooKeeper.create("/zj/zz","test".getBytes(),acls,CreateMode.PERSISTENT);
    }
}
