package org.geekbang.time.commonmistakes.collection.sublist;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toCollection;

public class SubListApplication {

    private static List<List<Integer>> data = new ArrayList<>();

    private static void wrong() throws Exception {
        ArrayList<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(toCollection(ArrayList::new));
        Class<ArrayList> arrayListClass = ArrayList.class;
        for (Field f : arrayListClass.getDeclaredFields()) {
            System.out.println(f.getName());
        }
        Field unsafeField = AbstractList.class.getDeclaredField("modCount");
        unsafeField.setAccessible(true);
        System.out.println("list modCount  " + unsafeField.getInt(list));

//        unsafeField = list.getClass().getDeclaredField("modCount");
//        int modCount = (int) unsafeField.get(null);
//
//        System.out.println("list modCount" + modCount);


        List<Integer> subList = list.subList(1, 4);
        System.out.println(subList);
        System.out.println("subList modCount " + unsafeField.getInt(subList));

//         unsafeField = subList.getClass().getField("modCount");
//        unsafeField.setAccessible(true);
//        int modCountsubList = (int) unsafeField.get(null);
//
//        System.out.println("subList modCount" + modCountsubList);
        subList.remove(1);
        System.out.println("subList modCount " + unsafeField.getInt(subList));
        System.out.println("list modCount  " + unsafeField.getInt(list));
        System.out.println(list);
        list.add(0);
        System.out.println("subList modCount " + unsafeField.getInt(subList));
        System.out.println("list modCount  " + unsafeField.getInt(list));
//        subList.add(0);
//        System.out.println("subList modCount " + unsafeField.getInt(subList));
//        System.out.println("list modCount  " + unsafeField.getInt(list));
        try {
            subList.forEach(System.out::println);
        } catch (Exception ex) {
//            ex.printStackTrace();
        }




//        test(200);
//        test(2);
//        test(null);//NullPointerException


    }

    private static void test(Integer i) {
        System.out.println(i == 200);


    }

    private static void test2() throws Exception {
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        List<String> subList = list.subList(0, 2);
        System.out.println(subList);
        list.remove(1);
//        System.out.println("subList:" + subList);
        System.out.println("list:" + list);


        List<Integer> integers = new ArrayList<>();
        System.out.println(integers.size());
        integers.add(1);

        Field elementData = integers.getClass().getDeclaredField("elementData");
        elementData.setAccessible(true);
        int arrayLen = ((Object[]) elementData.get(integers)).length;
        System.out.println(integers.size());
        System.out.println(arrayLen);
        for (int i = 1; i < 10; i++) {
            integers.add(i+1);
        }
        arrayLen = ((Object[]) elementData.get(integers)).length;
        System.out.println(arrayLen);
        System.out.println(integers.size());
        integers.add(11);
        arrayLen = ((Object[]) elementData.get(integers)).length;
        System.out.println(arrayLen);
        System.out.println(integers.size());



    }


   static volatile ArrayList<Integer> list = new ArrayList<Integer>();

    public static void main(String[] args) throws Exception {


//        new Thread(() -> {
//            int i =0;
//            while (true){
//                list.add(0,i++);
////                try {
////                    TimeUnit.MILLISECONDS.sleep(200);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//
//            }
//
//        }, "t1").start();
//
//         new Thread(() -> {
//             while (true){
////                 System.out.println(list);
////                 System.out.println(list.get(0));
//                 list.remove(0);
//             }
//
//        }, "t2").start();



//        oom();
        wrong();
        //right1();
//        right2();
        //oomfix();
    }

    private static void oom() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(rawList.subList(0, 1));
        }
    }

    private static void oomfix() {
        for (int i = 0; i < 1000; i++) {
            List<Integer> rawList = IntStream.rangeClosed(1, 100000).boxed().collect(Collectors.toList());
            data.add(new ArrayList<>(rawList.subList(0, 1)));
        }
    }

    private static void right1() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = new ArrayList<>(list.subList(1, 4));
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        subList.forEach(System.out::println);
    }

    private static void right2() {
        List<Integer> list = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toList());
        List<Integer> subList = list.stream().skip(1).limit(3).collect(Collectors.toList());
        System.out.println(subList);
        subList.remove(1);
        System.out.println(list);
        list.add(0);
        subList.forEach(System.out::println);
    }
}

