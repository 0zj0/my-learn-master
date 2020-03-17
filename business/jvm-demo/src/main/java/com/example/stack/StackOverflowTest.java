package com.example.stack;

/**
 * @author 张杰
 * @date 2020/3/12 18:50
 */
public class StackOverflowTest {

    //JVM 设置 栈大小默认1M
    // -Xss128K, -Xss 默认大小1M
    static int count = 0;

    static void redo(){
        count ++;
        redo();
    }

    /**
     * redo 一直在创建栈帧，未释放；当达到栈的最大值时，会抛出异常：StackOverflowError 栈溢出
     */
    public static void main(String[] args) {
        try {
            redo();
        } catch (Throwable e) {
            e.printStackTrace();
            //默认大小时，输出：26169
            //-Xss128K 后，输出：1091
            System.out.println(count);
        }
    }

}
