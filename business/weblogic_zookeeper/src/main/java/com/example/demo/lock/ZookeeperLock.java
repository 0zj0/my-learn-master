package com.example.demo.lock;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhangjie
 * @date 2020/4/23 11:40
 */
public class ZookeeperLock {

    private String server = "139.9.222.86:2181";

    private ZkClient zkClient;

    private static final String rootPath = "/zk-lock";

    public ZookeeperLock(){
        zkClient = new ZkClient(server,5000,20000);
        buildRoot();
    }

    /**
     * 创建根节点
     */
    private void buildRoot() {
        if(!zkClient.exists(rootPath)){
            zkClient.createPersistent(rootPath);
        }
    }

    /**
     * 获取锁
     */
    public Lock lock(String lockId, long timeout){
        //创建临时节点
        String nodePath = zkClient.createEphemeralSequential(rootPath + "/" + lockId, "w");
        Lock lockNode = new Lock(lockId, nodePath);
        //尝试激活锁
        tryActiveLock(lockNode);
        if(!lockNode.isActive()){
            try {
                synchronized (lockNode){
                    lockNode.wait(timeout);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        if(!lockNode.isActive()){
            throw new RuntimeException(" lock  timeout");
        }
        return lockNode;
    }

    /**
     * 释放锁
     * @param lock
     */
    public void unlock(Lock lock) {
        if (lock.isActive()) {
            zkClient.delete(lock.getPath());
        }
    }

    /**
     * 尝试激活锁
     * @param lockNode
     */
    private void tryActiveLock(Lock lockNode) {
        //获取根节点下的所有子节点
        List<String> list = zkClient.getChildren(rootPath)
                .stream().sorted().map(p -> rootPath + "/" + p)
                .collect(Collectors.toList());

        String firstNodePath = list.get(0);

        //当前节点为最小节点，直接激活
        if(firstNodePath.equals(lockNode.getPath())){
            lockNode.setActive(true);
        }else{
            //获取上一个节点（排序）
            String preNodePath = list.get(list.indexOf(lockNode.getPath())-1);
            zkClient.subscribeDataChanges(preNodePath, new IZkDataListener() {
                @Override
                public void handleDataChange(String s, Object o) throws Exception {

                }

                @Override
                public void handleDataDeleted(String s) throws Exception {
                    System.out.println("节点删除："+preNodePath);
                    tryActiveLock(lockNode);
                    synchronized (lockNode){
                        if (lockNode.isActive()) {
                            lockNode.notify(); // 释放了
                        }
                    }
                    zkClient.unsubscribeDataChanges(preNodePath,this);
                }
            });
        }

    }


}
