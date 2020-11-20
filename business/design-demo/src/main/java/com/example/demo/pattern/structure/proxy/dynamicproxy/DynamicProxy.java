package com.example.demo.pattern.structure.proxy.dynamicproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author zhangjie
 * @date 2020/11/20 17:36
 */
public class DynamicProxy {
    private Object object;
    public DynamicProxy(final Object object) {
        this.object = object;
    }
    public Object getProxyInstance(){
        /**
         * newProxyInstance(ClassLoader loader,Class<?>[] interfaces,InvocationHandler h)
         * loader: 代理对象类加载器
         * interfaces：代理对象实现的接口类型
         * h：指定动态处理器，执行目标对象的方法时,会触发事件处理器的方法
         */
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("动态代理前...");
                Object invoke = method.invoke(object, args);
                System.out.println("动态代理后...");
                return invoke;
            }
        });
    }
}
