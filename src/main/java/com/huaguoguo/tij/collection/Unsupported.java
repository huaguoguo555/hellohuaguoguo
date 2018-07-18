package com.huaguoguo.tij.collection;

import java.util.*;

/**
 * @Author:huaguoguo
 * @Description: 未支持的操作
 * @Date: 2018/7/10 16:30
 */
public class Unsupported {


    public static void main(String[] args) {
        String[] s = {"one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten",};
        List a = Arrays.asList(s);
        List<String> list = new ArrayList<String>(a);
        //Collection1.print(list);
        /*for (String s1 : list) {
            if (s1.equals("two")){
                list.remove("two");
            }
        }*/

        /*for (int i = 0; i < list.size(); i++) {
            list.remove("two");
        }*/

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            String next = iterator.next();
            iterator.remove();
        }
        //Collection1.print(list);
    }
}
