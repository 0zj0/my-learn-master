package com.example.demo.pattern.structure.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangjie
 * @date 2020/11/20 10:31
 */
public class BagComposite extends AbstractComponent{
    List<AbstractComponent> coms = new ArrayList<>();
    public BagComposite(String name,int quantity,int price) {
        this.setName(name);
        this.setQuantity(quantity);
        this.setPrice(price);
    }
    @Override
    public void print() {
        System.out.println("-----------"+getName()+" "+getCost()+"---------");
        for (AbstractComponent com : coms) {
            com.print();
        }
    }
    @Override
    public int getCost() {
        int cost = super.getCost();
        for (AbstractComponent com : coms){
            cost += com.getCost();
        }
        return cost;
    }
    public void add(AbstractComponent component){
        coms.add(component);
    }
    public void remove(AbstractComponent component){
        coms.remove(component);
    }
    public AbstractComponent getChild(int index){
        return coms.get(index);
    }
}

