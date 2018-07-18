package com.huaguoguo.tij.collection;

import java.util.*;

/**
  * @Author:huaguoguo
  * @Description: 只读集合
  * @Date: 2018/7/11 14:58
  */
public class ReadOnly {

    public static void main(String[] args) {
        Collection c = new ArrayList();
        Collection1.fill(c); // Insert useful data
        c = Collections.unmodifiableCollection(c);
        Collection1.print(c); // Reading is OK
        //c.add("one"); // Can't change it
        List a = new ArrayList();
        Collection1.fill(a);
        a = Collections.unmodifiableList(a);
        ListIterator lit = a.listIterator();
        while (lit.hasNext()){
            System.out.print(lit.next() + " "); // Reading OK
        }
        System.out.println();
        System.out.println("===============");
        //! lit.add("one"); // Can't change it
        Set s = new HashSet();
        Collection1.fill(s);
        s = Collections.unmodifiableSet(s);
        Collection1.print(s); // Reading OK
        //! s.add("one"); // Can't change it
        Map m = new HashMap();
        Map1.fill(m, Map1.testData1);
        m = Collections.unmodifiableMap(m);
        Map1.print(m); // Reading OK
        //! m.put("Ralph", "Howdy!");
    }
}
