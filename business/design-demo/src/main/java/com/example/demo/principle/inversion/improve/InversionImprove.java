package com.example.demo.principle.inversion.improve;

/**
 * @author zhangjie
 * @date 2020/11/11 16:32
 */
public class InversionImprove {

    /**
     * 基础方式仅实现了出行，但是扩展存在问题，当出行方式出行变更时，改动较大；
     * 出行方式有很多中，基于依赖倒置原则：我们抽象出来了出行方法，具体的出行方式实现抽象处理的出行方法；
     * @param args
     */
    public static void main(String[] args) {
        Person person = new Person();
        person.travel(new CarTravel());
        person.travel(new ShipTravel());
    }
}
interface Travel{
    void travelMethod();
}
class CarTravel implements Travel{
    @Override
    public void travelMethod() {
        System.out.println("汽车出行...");
    }
}
class ShipTravel implements Travel{
    @Override
    public void travelMethod() {
        System.out.println("轮船出行...");
    }
}
class Person{
    public void travel(Travel travel){
        travel.travelMethod();
    }
}
