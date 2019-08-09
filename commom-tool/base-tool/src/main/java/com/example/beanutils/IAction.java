package com.example.beanutils;

@FunctionalInterface
public interface IAction<T> {

    void run(T param);

}
