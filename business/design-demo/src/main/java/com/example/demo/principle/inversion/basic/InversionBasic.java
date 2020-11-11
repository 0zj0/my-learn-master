package com.example.demo.principle.inversion.basic;

/**
 * @author zhangjie
 * @date 2020/11/11 16:24
 */
public class InversionBasic {

    public static void main(String[] args) {
        Person person = new Person();
        person.travel(new Travel());
    }

}
class Travel{
    public void travelMethod(){
        System.out.println("出行方式...");
    }
}
class Person{
    public void travel(Travel travel){
        travel.travelMethod();
    }
}
