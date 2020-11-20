package com.example.demo.pattern.structure.proxy.staticporxy;

/**
 * @author zhangjie
 * @date 2020/11/20 17:23
 */
public class BuyHouseProxy implements BuyHouse {
    private BuyHouse buyHouse;
    public BuyHouseProxy(BuyHouse buyHouse) {
        this.buyHouse = buyHouse;
    }
    @Override
    public void buyHosue() {
        System.out.println("静态代理前处理....");
        buyHouse.buyHosue();
        System.out.println("静态代理后处理....");
    }
}
