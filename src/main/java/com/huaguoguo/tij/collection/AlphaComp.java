package com.huaguoguo.tij.collection;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @Author:huaguoguo
 * @Description: 可比较与比较器
 * @Date: 2018/7/11 14:24
 */
public class AlphaComp implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        String s1 = ((String) o1).toLowerCase();
        String s2 = ((String) o2).toLowerCase();
        return s1.compareTo(s2);
    }

    public static void main(String[] args) {
        String[] s = Array1.randStrings(4, 10);
        Array1.print(s);
        AlphaComp ac = new AlphaComp();
        Arrays.sort(s, ac);
        Array1.print(s);
        // Must use the Comparator to search, also:
        int loc = Arrays.binarySearch(s, s[3], ac);
        System.out.println("Location of " + s[3] + " = " + loc);
    }
}
