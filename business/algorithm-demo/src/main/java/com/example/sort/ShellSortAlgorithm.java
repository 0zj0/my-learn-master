package com.example.sort;

/**
 * 希尔排序
 * @author zhangjie
 * @date 2019/2/19 17:13
 */
public class ShellSortAlgorithm {

    /**
     *      希尔排序是希尔（Donald Shell）于1959年提出的一种排序算法。希尔排序也是一种插入排序，它是简单插入排序经过
     * 改进之后的一个更高效的版本，也称为缩小增量排序，同时该算法是冲破O(n2）的第一批算法之一。它与插入排序的不同之处
     * 在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序。
     *      希尔排序是把记录按下表的一定增量分组，对每组使用直接插入排序算法排序；随着增量逐渐减少，每组包含的关键词越
     * 来越多，当增量减至1时，整个文件恰被分成一组，算法便终止。
     *
     * 算法描述
     *      我们来看下希尔排序的基本步骤，在此我们选择增量gap=length/2，缩小增量继续以gap = gap/2的方式，这种增量选择我们
     * 可以用一个序列来表示，{n/2,(n/2)/2...1}，称为增量序列。希尔排序的增量序列的选择与证明是个数学难题，我们选择的这
     * 个增量序列是比较常用的，也是希尔建议的增量，称为希尔增量，但其实这个增量序列不是最优的。此处我们做示例使用希尔增量。
     *     先将整个待排序的记录序列分割成为若干子序列分别进行直接插入排序，具体算法描述：
     *     1 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
     *     2 按增量序列个数k，对序列进行k 趟排序；
     *     3 每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。仅增量因子为1 时，
     *     整个序列作为一个表来处理，表长度即为整个序列的长度。
     *
     * 思考：最后一轮，会直接变成插入排序实现。。。
     *
     */
    public static void ShellSort(int[] arr) {
        if(arr == null || arr.length <= 1){
            return;
        }
        int len = arr.length;
        int gap = len / 2;
        while (gap > 0){
            for(int i = gap; i < len; i++){
                int currentValue = arr[i];
                int preIndex = i - gap;
                while (preIndex >= 0 && arr[preIndex] > currentValue){
                    arr[preIndex+gap] = arr[preIndex];
                    preIndex -= gap;
                }
                arr[preIndex+gap] = currentValue;
            }
            gap /= 2;
        }
    }

    public static void main(String[] args) {
        int[] arr = {3,15,9,20,11,16,10,15,7,2};
        for(int a : arr){
            System.out.print(a+"\t");
        }
        System.out.println();
        ShellSort(arr);
        for(int a : arr){
            System.out.print(a+"\t");
        }
    }

}
