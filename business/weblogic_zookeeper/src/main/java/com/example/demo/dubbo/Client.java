package com.example.demo.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import javafx.application.Application;

import java.io.IOException;

/**
 * 服务消费者
 * @author 张杰
 * @date 2020/4/19 19:36
 */
public class Client {
    UserService service;

    // URL 远程服务的调用地址
    public UserService buildService(String url) {

        ApplicationConfig config = new ApplicationConfig("young-app");
        //构建一个应用对象
        ReferenceConfig<UserService> referenceConfig = new ReferenceConfig<>();
        referenceConfig.setApplication(config);
        referenceConfig.setInterface(UserService.class);

        referenceConfig.setRegistry(new RegistryConfig("zookeeper://139.9.222.86:2181"));
        referenceConfig.setTimeout(5000);
        // 透明化
        this.service = referenceConfig.get();
        return service;
    }

    static int i = 0;

    public static void main(String[] args) throws IOException {
        Client client1 = new Client();
        client1.buildService("");
        String cmd;
        while (!(cmd = read()).equals("exit")) {
            UserVo u = client1.service.getUser(Integer.parseInt(cmd));
            System.out.println(u);
        }
    }

    private static String read() throws IOException {
        byte[] b = new byte[1024];
        int size = System.in.read(b);
        return new String(b, 0, size).trim();
    }

}
