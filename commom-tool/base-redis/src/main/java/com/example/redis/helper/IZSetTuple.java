package com.example.redis.helper;

/**
 * @author zhangjie
 * @date 2020/11/2 16:55
 */
public interface IZSetTuple extends Comparable<IZSetTuple> {

    /**
     * 成员名称
     *
     * @return
     */
    String getMember();

    /**
     * 成员成绩
     *
     * @return
     */
    long getScore();
}
