package com.example.jvm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 * @author 张杰
 * @date 2020/3/11 17:29
 */
public class MyClassLoaderTest {

    static class MyClassLoader extends ClassLoader{

        private String classPath;

        public MyClassLoader(String classPath) {
            this.classPath = classPath;
        }


        /**
         * 重要方法：类加载
         * @param name
         * @return
         * @throws ClassNotFoundException
         */
        @Override
        public Class<?> loadClass(String name) throws ClassNotFoundException {
            return super.loadClass(name);
        }

        /**
         * 类查找
         * @param name
         * @return
         * @throws ClassNotFoundException
         */
        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            try {
                byte[] data = loadByte(name);
                return defineClass(name,data,0,data.length);
            } catch (Exception e) {
                e.printStackTrace();
                throw new ClassNotFoundException();
            }
        }

        /**
         * 读取指令路径的类
         * @param name
         * @return
         */
        private byte[] loadByte(String name) throws Exception {
            name = name.replaceAll("\\.","/");
            FileInputStream fis = new FileInputStream(classPath + "/"+name +".class");
            int len = fis.available();
            byte[] data = new byte[len];
            fis.read(data);
            fis.close();
            return data;
        }
    }

    /**
     * 测试方法
     * @param args
     */
    public static void main(String[] args) throws Exception {
        MyClassLoader myClassLoader = new MyClassLoader("D:/code/jvm_test");
        //加载类
        Class clazz = myClassLoader.loadClass("com.example.common.User1");
        System.out.println("自定义加载类...");
        //类实例化
        Object object = clazz.newInstance();
        System.out.println("类实例化...");
        //反射调用类方法
        Method method = clazz.getDeclaredMethod("print",null);
        method.invoke(object,null);
        //打印当前类加载器
        System.out.println(clazz.getClassLoader().getClass().getName());
        //运行结果：com.example.jvm.MyClassLoaderTest$MyClassLoader
    }
}
