package com.example.will.thread_knowledge;

import org.junit.Test;

import java.util.HashMap;
import java.util.Random;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    int data;
    private HashMap<String, Integer> map = new HashMap<>();

    @Test
    public void addition_isCorrect() {
//        assertEquals(4, 2 + 2);
        System.out.println("A_" + Thread.currentThread().getName() + "_" + data);
    }

    @Test
    public void commonShareThreadLocal() {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    data = new Random().nextInt();
                    MyThreadLocalData.newInstance().setName("Name_" + data);
                    MyThreadLocalData.newInstance().setAge(data);
                    new ClassA().getData();
                    new ClassB().getData();
                }
            }).start();
        }
    }

    public class ClassA {
        public void getData() {
            MyThreadLocalData data = MyThreadLocalData.newInstance();
            System.out.println("A_" + data.getName() + "_" + data.getAge());
        }
    }

    public class ClassB {
        public void getData() {
            MyThreadLocalData data = MyThreadLocalData.newInstance();
            System.out.println("B_" + data.getName() + "_" + data.getAge());
        }
    }

    public static class MyThreadLocalData {

        private MyThreadLocalData() {
        }

        public static MyThreadLocalData newInstance() {
            MyThreadLocalData instance = map.get();
            if (instance == null) {
                instance = new MyThreadLocalData();
                map.set(instance);
            }
            return instance;
        }

        public static ThreadLocal<MyThreadLocalData> map = new InheritableThreadLocal<>();

        public String name;
        public int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
