package com.ezfun.guess.demo;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

/**
 * @author SoySauce
 * @date 2019/4/28
 */
public class MyDemo {

    List<String> list = Arrays.asList("a", "b", "c");

    public static <T extends Comparable<T>>
    void sort(List<T> list, MyComparable comp) {
    }


    public <T extends Comparable<T>>
    void sort2(List<T> list, MyComparable2 comp) {
    }


    void testSort() {
        sort(list, (a, b) -> a.compareTo(b));
    }

    void testSort2() {
        sort(list, Comparable::compareTo);
    }

//    void testSort3() {
//        sort(list, MyComparable4::compareTo);
//    }

    void testSort2_1() {
        sort2(list, Comparable::compareTo);
    }

    void testSort2_2() {
        sort2(list, Sort::compareTo);
    }

//    void testSort2_3() {
//        sort2(list, <Comparable>(Comparable a, Comparable s)-> 1);
//    }
//
//
//
//
//    void testSort2_3() {
//        sort2(list, (Comparator<String>)(a, b) -> a.compareTo(b));
//    }


    public void testHtml2Bean() throws Exception {
        Html2Bean<String, InputStream> bean = FileInputStream::new;
        Html2Bean<String, Integer> html2Bean = a -> 1;
        Integer z = html2Bean.converter("z");

        System.out.println(z);


    }


    public static class Sort {

        //        @Override
        public int compareTo(Object o) {
            return 0;
        }

        static <T extends Comparable<T>> int compareTo(T t, T t1) {
            return 0;
        }
    }

    String getParam2() throws Exception {
        List<String> list = Arrays.asList("a", "b", "c");
//        sort(list, <T extends Comparable<T>>(a, b) -> a.compareTo(b));

//        sort2(list, File::compareTo());
        Html2Bean<String, Integer> html2Bean = (obj) -> 1;
        Html2Bean<String, Integer> html2Bean2 = obj -> {
            System.out.println(obj);
            return Integer.valueOf(obj);
        };
        Integer converter = html2Bean2.converter("2");
        System.out.println(converter);

        return "1";
    }

    public static void main(String[] args) throws Exception {
        String param2 = MyDemo.class.newInstance().getParam2();
    }


    public interface NCopies {
        public <T extends Cloneable> List<T> getCopies(T seed, int num);
    }


//    public interface NCopies{
//        public <T extends Cloneable> List<T> getCopies(T seed, int num);
//    }

    //Inferred types for arguments also supported for generic methods
    void test() {

//This code will give a compilation error,
//As the lambda is meaningless without a context
//        (<T extends Cloneable> (seed, num) -> {
//            List<T> list = new ArrayList<>();
//            for(int i=0; i<num; i++)
//                list.add(seed.clone());
//            return list;
//        }).getCopies(new CloneableClass(), 5);
//
//
////        However, the following would be perfectly alright, because there is an assignment context for the lambda.
//
//        NCopies nCopies = <T extends Cloneable> (seed, num) -> {
//            List<T> list = new ArrayList<>();
//            for(int i=0; i<num; i++)
//                list.add(seed.clone());
//            return list;
//        };
//
//        nCopies.getCopies(new CloneableClass(), 5);
//
//
//
//        NCopies nCopies = <T extends Cloneable> (seed, num) -> {
//            List<T> list = new ArrayList<>();
//            for(int i=0; i<num; i++)
//                list.add(seed.clone());
//            return list;
//        };
    }

}
