package com.example.demo.job;

import org.I0Itec.zkclient.ZkClient;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zhangjie
 * @date 2020/4/21 16:15
 */
public class MasterResolve {

    private String server = "139.9.222.86:2181";
    private ZkClient zkClient;
    private static final String rootPath = "/my-master";
    private static final String servicePath = rootPath + "/service";
    private String nodePath;
    private volatile boolean master = false;
    private static MasterResolve resolve;

    private static MasterResolve ourInstance = new MasterResolve();

    public static MasterResolve getInstance() {
        return ourInstance;
    }

    private MasterResolve() {
        //创建连接
        zkClient = new ZkClient(server,20000,50000);
        //创建根节点
        buildRootNode();
        //创建服务节点
        createServiceNode();
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建根节点
     */
    private void buildRootNode(){
        if(!zkClient.exists(rootPath)){
            zkClient.createPersistent(rootPath);
        }
    }

    /**
     * 创建服务节点
     */
    private void createServiceNode(){
        //创建临时序号节点
        nodePath = zkClient.createEphemeralSequential(servicePath, "slave");
        System.out.println("创建service节点:" + nodePath);
        //主节点处理
        initMaster();
        //添加监控
        initListener();
    }

    /**
     * 主节点处理
     */
    private void initMaster() {
        //判断是否存在主节点，存在，则return；不存在，选举主节点
        boolean existMaster = zkClient.getChildren(rootPath).stream()
                .map(p -> rootPath + "/" + p)   //拼接完整节点路径
                .map(p -> zkClient.readData(p)) //读取节点value值
                .allMatch(d -> "master".equals(d));

        if(existMaster){
            System.out.println("已存在主节点");
            return;
        }

        System.out.println("不存在主节点，选举主节点");
        //主节点选举
        doElection();
    }

    /**
     * 添加持久监控
     * subscribeDataChanges : 监听节点数据的变化
     */
    private void initListener() {
        //监听子节点变更
        zkClient.subscribeChildChanges(rootPath,(parentPath, currentChilds) -> {
            System.out.println("节点监听：[parentPath:"+parentPath+"],[currentChilds:"+currentChilds.toString()+"]");
            doElection();
        });
    }

    /**
     *  节点选举
     */
    private void doElection() {
        //1. 获取所有服务节点
        Map<String, Object> map = zkClient.getChildren(rootPath).stream()
                .map(p -> rootPath + "/" + p)  //拼接完整节点路径
                .collect(Collectors.toMap(p -> p, p -> zkClient.readData(p)));

        //2.存在主节点
        for(Map.Entry<String, Object> entry : map.entrySet()){
            String mapKey = entry.getKey();
            String mapValue = entry.getValue().toString();
            if("master".equals(mapValue)){
                System.out.println("存在master:" + mapKey);
                return;
            }
        }

        //3.设置节点序号最小的未master 节点
        map.keySet().stream().sorted().findFirst().ifPresent(p -> {
            System.out.println("当前当选master:" + p);
            zkClient.writeData(p,"master");
        });
    }

    public static void main(String[] args) {
        MasterResolve masterResolve = new MasterResolve();
    }
}
