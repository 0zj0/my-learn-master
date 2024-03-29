package com.example.demo.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.I0Itec.zkclient.ZkClient;

import java.lang.instrument.Instrumentation;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * 数据收集，上报
 * @author 张杰
 * @date 2020/4/19 16:33
 */
public class Agent {
    private static Agent ourInstance = new Agent();
    private String server = "139.9.222.86:2181";
    private ZkClient zkClient;
    private static final String rootPath = "/zj-manager";
    private static final String servicePath = rootPath + "/service";
    private String nodePath;
    private Thread stateThread;

    public static Agent getInstance() {
        return ourInstance;
    }

    private Agent() {
    }

    //javaagent 数据监控
    public static void remain(String args, Instrumentation instrumentation){
        Agent.getInstance().init();
    }

    public void init(){
        zkClient = new ZkClient(server,5000,10000);
        System.out.println("zookeeper 连接成功" + server);

        //创建根节点
        buildRoot();
        // 创建临时节点
        createServerNode();

        // 启动更新的线程
        stateThread = new Thread(() -> {
            while (true) {
                updateServerNode();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "zk_stateThread");
        stateThread.setDaemon(true);
        stateThread.start();

    }

    // 数据写到 当前的临时节点中去
    public void updateServerNode() {
        zkClient.writeData(nodePath, getOsInfo());
    }

    //生成服务节点（临时节点）
    public void createServerNode(){
        //临时，序号节点
        nodePath = zkClient.createEphemeralSequential(server,getOsInfo());
        System.out.println("创建节点:" + nodePath);
    }

    //更新服务节点状态
    public String getOsInfo(){
        OsBean osBean = new OsBean();
        osBean.lastUpdateTime = System.currentTimeMillis();
        osBean.ip = getLocalIp();
        osBean.cpu= CPUMonitorCalc.getInstance().getProcessCpu();
        MemoryUsage memoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        osBean.usedMemorySize = memoryUsage.getUsed() /1024 / 1024;
        osBean.usableMemorySize = memoryUsage.getMax() / 1024 / 1024;
        osBean.pid = ManagementFactory.getRuntimeMXBean().getName();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(osBean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //获取ip信息
    public static String getLocalIp(){
        InetAddress addr = null;
        try {
            addr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
        return addr.getHostAddress();
    }

    //创建根节点
    public void buildRoot(){
        if(!zkClient.exists(rootPath)){
            //创建持久根节点
            zkClient.createPersistent(rootPath);
        }
    }
}
