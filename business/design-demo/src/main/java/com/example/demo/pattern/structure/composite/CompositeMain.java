package com.example.demo.pattern.structure.composite;

/**
 * @author zhangjie
 * @date 2020/11/20 10:48
 */
public class CompositeMain {
    public static void main(String[] args) {
        BagComposite bigBag = new BagComposite("大包",1,5);
        BagComposite smartBag1 = new BagComposite("小包1",1,2);
        BagComposite smartBag2 = new BagComposite("小包2",1,2);
        AbstractComponent food1 = new FoodLeaf("食物1",2,100);
        AbstractComponent food2 = new FoodLeaf("食物2",1,300);
        AbstractComponent food3 = new FoodLeaf("食物3",1,600);
        AbstractComponent food4 = new FoodLeaf("食物4",2,400);
        smartBag1.add(food1);
        smartBag1.add(food2);
        smartBag2.add(food3);
        bigBag.add(smartBag1);
        bigBag.add(smartBag2);
        bigBag.add(food4);
        System.out.println(bigBag.getCost());
        bigBag.print();
    }
}
