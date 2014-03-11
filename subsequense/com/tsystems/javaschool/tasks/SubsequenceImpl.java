package com.tsystems.javaschool.tasks;


import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Aleksandr
 * Date: 15.01.13
 * Time: 22:18
 */
public class SubsequenceImpl implements Subsequence {
    public SubsequenceImpl() {
    }

    /**
     * Method realises the algorithm of finding of possibility to get
     * sequence z by removing items from y. Method just delete items
     * from one list if it not equal to item in another list.
     *
     * @param z input string in infix notation form
     * @param y input string in infix notation form
     * @return if it's possible to get sequence z from y, return <code>true</code>
     * in other case return <code>false</code>
     */
    @Override
    public boolean find(List z, List y) {
        try {
            //return value
            boolean b = false;
            //input lists
            List<Object> o1 = new LinkedList(z);
            List<Object> o2 = new LinkedList(y);
            //accumulator for subsequence
            List<Object> o3 = new LinkedList();
            int n = o1.size();
            int j = 0;
            //for each item of sequence z and while size of y>0
            for (int i = 0; i < n && o2.size() > 0; i++) {

                //delete all items until y is not empty or occurs equal object
                while (o2.size() > 0) {
                    if (o1.get(i) != o2.get(j)) {
                        o2.remove(j);
                        j = 0;
                    } else {
                        //adding item to acc list
                        o3.add(o2.get(j));
                        j++;
                        break;
                    }
                }


            }
            //return true iff size of sequence x equal to size of acc, else false
            if (o1.size() == o3.size()) b = true;


            return b;
        } catch (Exception e) {
            //System.out.println(e.getMessage());
            return false;
        }
    }


    public static void main(String[] args) {
        Subsequence s = new SubsequenceImpl();
        boolean b = s.find(Arrays.asList("A", "B", "C", "DL"), Arrays.asList("BD", "A", "ABC", "B", "M", "D", "M", "C", "DC", "D"));
        System.out.println(b); // Результат: false
        b = s.find(Arrays.asList("A", "B", "C", "D"), Arrays.asList("BD", "A", "ABC", "B", "M", "D", "M", "C", "DC", "D"));
        System.out.println(b); // Результат: true
    }

}
