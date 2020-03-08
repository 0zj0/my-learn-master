package com.example.pool;

/**
 * @author 张杰
 * @date 2020/3/8 12:13
 */
public class test {

    /**
     * 结果：
     * i=1,j=6
     * i=2,j=7
     * i=3,j=8
     * i=4,j=9
     * i=5,j=10
     * i=6,j=11
     *
     * @param args
     */
    public static void main(String[] args) {
        int i=0,j=0;
        retry:
        for(;;){
            i++;
            for(;;){
                j++;
                if(j > 10){
                    System.out.println("i="+i+",j="+j);
                    break retry;
                }
                if(j > 5){
                    System.out.println("i="+i+",j="+j);
                    continue retry;
                }
            }
        }
    }

}
