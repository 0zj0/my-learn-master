package com.example.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * @author zhangjie
 * @date 2020/11/3 16:15
 */
public class BubbleSortAlgorithm {

    /***
     *      冒泡排序是一种简单的排序算法。它重复地走访过要排序的数列，一次比较两个元素，如果它们的顺序错误就把它们交换过来。
     *  走访数列的工作是重复地进行直到没有再需要交换，也就是说该数列已经排序完成。这个算法的名字由来是因为越小的元素会经
     *  由交换慢慢“浮”到数列的顶端。
     *  算法描述
     *     1 比较相邻的元素。如果第一个比第二个大，就交换它们两个；
     *     2 对每一对相邻元素作同样的工作，从开始第一对到结尾的最后一对，这样在最后的元素应该会是最大的数；
     *     3 针对所有的元素重复以上的步骤，除了最后一个；
     *     重复步骤1~3，直到排序完成。
     *  算法分析
     *      最佳情况：T(n) = O(n)   最差情况：T(n) = O(n2)   平均情况：T(n) = O(n2)
     */
    public static int[] bubbleSort(int[] arr){
        if(arr == null || arr.length <= 1){
            return arr;
        }
        int length = arr.length;
        int count = 0;
        for(int i = 0 ; i < length ; i++){
            for(int j = 0 ; j < length -1 ; j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    count++;
                }
            }
        }
        System.out.println("交换次数:"+ count);
        return arr;
    }

    private static int countCnt = 0;
    /**
     * 冒泡排序的递归实现：
     * 实现逻辑：冒泡排序的实现原理在于选举，第一轮选举出最大值存放到最后一位，第二轮选举出第二大值存放到倒数第二位
     *     以此类推，直到循环结束。
     *     递归实现在于：只有存在相邻的位置大小顺序不一致，则进行值交换，开始递归；
     *     普通实现：循环最大次数(n-1)+(n-2)+...+0 = n*(n-1)/2; 递归实现最大次数：n*(n-1)/2
     * @param arr
     * @return
     */
    public static int[] bubbleSort_Recursion(int[] arr){
        if(arr == null || arr.length <= 1){
            return arr;
        }
        boolean isReplace = false;
        for(int i= 0; i < arr.length -1; i++){
            if(arr[i] > arr[i+1]){
                int temp = arr[i];
                arr[i] = arr[i+1];
                arr[i+1] = temp;
                isReplace = true;
                countCnt++;
            }
        }
        if(isReplace){
            //开始递归
            return bubbleSort_Recursion(arr);
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr1 = SortConsts.arr;
        int[] arr2 = Arrays.copyOf(arr1,arr1.length);
        for(int a : SortConsts.arr){
            System.out.print(a+"\t");
        }
        System.out.println("\n普通冒泡排序:");
        int[] sort1 = bubbleSort(arr1);
        for(int a : sort1){
            System.out.print(a+"\t");
        }
        System.out.println("\n递归冒泡排序:");
        int[] sort2 = bubbleSort_Recursion(arr2);
        System.out.println("交换次数:"+countCnt);
        for(int a : sort2){
            System.out.print(a+"\t");
        }
    }
}
