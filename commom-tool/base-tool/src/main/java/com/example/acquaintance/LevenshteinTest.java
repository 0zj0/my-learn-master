package com.example.acquaintance;

import info.debatty.java.stringsimilarity.Levenshtein;

import java.util.Arrays;
import java.util.List;

/**
 * @author zhangjie
 * @date 2020/10/20 17:58
 */
public class LevenshteinTest {


    private static String target = "榴芒一族(果肉满杯西乡天虹店)";
    private static List<String> sourceList = Arrays.asList(
            "榴芒一族(西乡天虹店)",
            "榴芒一族(果肉满杯西乡天虹店)",
            "榴芒一族(肉满杯西乡天虹店)",
            "榴芒一族(果肉满杯西乡天虹店",
            "客语-客家菜(宝安大仟里店)",
            "奈雪の茶(宝安汇一城店)",
            "面包新语(宝安大仟里店)"
    );

    public static void main(String[] args) {
        Levenshtein levenshtein = new Levenshtein();
        for (String source : sourceList){
            System.out.println(levenshtein.distance(source,target));
        }

    }

}
