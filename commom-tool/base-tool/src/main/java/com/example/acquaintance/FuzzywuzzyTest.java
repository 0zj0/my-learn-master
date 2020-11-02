package com.example.acquaintance;

import me.xdrop.fuzzywuzzy.FuzzySearch;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangjie
 * @date 2020/10/20 17:26
 */
public class FuzzywuzzyTest {

    /**
     * Fuzzywuzzy:
     *
     * Levenshtein Distance 算法，又叫 Edit Distance 算法，是指两个字符串之间，
     * 由一个转成另一个所需的最少编辑操作次数。许可的编辑操作包括将一个字符替换成另一个字符，
     * 插入一个字符，删除一个字符。一般来说，编辑距离越小，两个串的相似度越大。
     */
    //
    private static String target = "榴芒一族(果肉满杯西乡天虹店)";
    private static List<String> sourceList = Arrays.asList(
            "榴芒一族(西乡天虹店)",
            "榴芒一族果肉满杯(西乡天虹店)",
            "榴芒一族(果肉满杯西乡天虹店)",
            "榴芒一族(果肉满杯西乡天虹店adaaaf)",
            "榴芒一族(肉满杯西乡天虹店)",
            "榴芒一族(果肉满杯西乡天虹店",
            "榴芒一族(西乡天虹店果肉满杯)",
            "客语-客家菜(宝安大仟里店)",
            "奈雪の茶(宝安汇一城店)",
            "面包新语(宝安大仟里店)",
            "面包新语(西乡天虹店)"
            );

    public static void main(String[] args) {
        for (String source : sourceList){
            //全匹配，对顺序敏感
            System.out.println(FuzzySearch.ratio(source,target));
        }
    }


    public static void main1(String[] args) {
        for (String source : sourceList){
            //搜索匹配(部分匹配)，对顺序敏感
            System.out.println(FuzzySearch.partialRatio(source,target));
        }
    }

    public static void main2(String[] args) {
        for (String source : sourceList){
            //首先做排序，然后全匹配，对顺序不敏感(也就是更换单词位置之后，相似度依然会很高)
            System.out.println(FuzzySearch.tokenSortRatio(source,target));
        }
    }

    public static void main3(String[] args) {
        for (String source : sourceList){
            //首先取集合(去掉重复词)，然后全匹配，对顺序不敏感，第二个字符串包含第一个字符串就100
            System.out.println(FuzzySearch.tokenSetPartialRatio(source,target));
        }
    }
}
