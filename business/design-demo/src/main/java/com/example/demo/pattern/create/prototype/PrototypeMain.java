package com.example.demo.pattern.create.prototype;

/**
 * @author zhangjie
 * @date 2020/11/18 16:46
 */
public class PrototypeMain {

    public static void main(String[] args) throws CloneNotSupportedException {
        Sheep sheep = new Sheep("tom",1,"白色");
        Sheep friend = new Sheep("jack",1,"白色");
        sheep.setFriend(friend);
        //Sheep Sheep2 = (Sheep)sheep.clone();
        Sheep Sheep2 = (Sheep)sheep.deepClone();
        System.out.println(sheep.toString());
        System.out.println(Sheep2.toString());
        System.out.println(sheep == Sheep2);
        System.out.println(sheep.getFriend() == Sheep2.getFriend());
    }

}
