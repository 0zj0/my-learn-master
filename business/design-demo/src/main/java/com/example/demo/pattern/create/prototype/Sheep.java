package com.example.demo.pattern.create.prototype;

import lombok.Data;

import java.io.*;

/**
 * @author zhangjie
 * @date 2020/11/18 16:43
 */
@Data
public class Sheep implements Cloneable, Serializable {
    private String name;
    private int age;
    private String color;
    private Sheep friend;
    @Override
    protected Object clone() throws CloneNotSupportedException {
        //基础数据类型转换
        Sheep sheep = (Sheep) super.clone();
        //应用数据类型转换
        if(sheep.getFriend() != null){
            Sheep friend = (Sheep) sheep.getFriend().clone();
            sheep.setFriend(friend);
        }
        return sheep;
    }
    public Object deepClone(){
        //创建刘对象
        ByteArrayOutputStream bos = null;   //字节输出流
        ObjectOutputStream oos = null;      //对象输出流
        ByteArrayInputStream bis = null;    //字节输入流
        ObjectInputStream ois = null;       //对象输入流

        try {
            //序列化
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            oos.writeObject(this);

            //反序列化
            bis = new ByteArrayInputStream(bos.toByteArray());
            ois = new ObjectInputStream(bis);
            Object object = ois.readObject();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                bos.close();
                oos.close();
                bis.close();
                ois.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public Sheep(String name, int age, String color) {
        this.name = name;
        this.age = age;
        this.color = color;
    }
}
