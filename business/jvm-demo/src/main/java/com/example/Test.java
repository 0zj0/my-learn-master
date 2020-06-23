package com.example;

/**
 * @author zhangjie
 * @date 2020/5/27 16:39
 */
public class Test {

    public static void main(String[] args) {
        int i= 4;
        int b = i++;
        int c = ++i;
        System.out.println(b);
        System.out.println(c);
    }

    /**
     *  nt i= 4; int b = i++; int c = ++i;
     *   0: iconst_4    //将常量 4 压入操作数栈顶
     *   1: istore_1    //将操作数栈顶元素弹出并压入到局部变量表中1号槽位，也就是 i= 4
     *   2: iload_1     //将1号槽位的元素压入操作数栈顶
     *   3: iinc        1, 1  //将局部变量表中的1号槽位的元素自增1，此时局部变量表中的1号元素值为1，也就是i=5 ,未压入操作数栈，栈顶数据仍然为4
     *   6: istore_2    //将操作数栈顶元素弹出并压入到局部变量表中2号槽位，也就是 b = 4
     *   7: iinc          1, 1 // i =6, 但栈顶数据仍然为4
     *   10: iload_1       // i = 6, 将1号槽位的元素压入操作数栈顶, 栈顶数据 为6
     *   11: istore_3      // c = 6
     */

}
