package com.example.demo.dubbo;


import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.ServiceConfig;

import java.io.IOException;

/**
 * 服务提供
 * @author 张杰
 * @date 2020/4/19 19:16
 */
public class Server {

    public void openServer(int port){
        //构建应用
        ApplicationConfig config = new ApplicationConfig();
        config.setName("simple-app");

        //通信协议
        ProtocolConfig protocolConfig = new ProtocolConfig("dubbo",port);
        protocolConfig.setThreads(200);

        ServiceConfig<UserService> serviceConfig = new ServiceConfig<>();
        serviceConfig.setApplication(config);
        serviceConfig.setProtocol(protocolConfig);
        serviceConfig.setRegistry(new RegistryConfig("zookeeper://139.9.222.86:2181"));
        serviceConfig.setInterface(UserService.class);
        UserServiceImpl ref = new UserServiceImpl();
        serviceConfig.setRef(ref);

        //开始提供服务
        serviceConfig.export();
        System.out.println("服务已开启！端口："+serviceConfig.getExportedUrls());
        ref.setPort(serviceConfig.getExportedUrls().get(0).getPort());
        System.out.println("UserServiceImpl port:"+ref.getPort());
    }

    public static void main(String[] args) throws IOException {
        new Server().openServer(-1);
        System.in.read();
    }

}
