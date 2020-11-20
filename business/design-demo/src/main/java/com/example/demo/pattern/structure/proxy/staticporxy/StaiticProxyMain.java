package com.example.demo.pattern.structure.proxy.staticporxy;

/**
 * @author zhangjie
 * @date 2020/11/20 17:25
 */
public class StaiticProxyMain {
    public static void main(String[] args) {
        BuyHouse buyHouse = new BuyHouseImpl();
        BuyHouseProxy proxy = new BuyHouseProxy(buyHouse);
        proxy.buyHosue();
    }
}
