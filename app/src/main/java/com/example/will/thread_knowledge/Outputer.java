package com.example.will.thread_knowledge;

public class Outputer {
    //        无锁
    public void output(String name) {
        int len = name.length();
        for (int i = 0; i < len; i++) {
            System.out.print(name.charAt(i));
        }
        System.out.println();
    }

    //        output1和output2一样,把内部全锁等于方法锁,但是锁却不一样,一个是字节码一个this.
    //        output1和output3一样是字节码.
    //        锁必须唯一必须唯一必须唯一!
    public void output1(String name) {
        synchronized (Outputer.class) {
            int len = name.length();
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }
    }

    //        静态所使用的锁是类的字节码,而这里是此类的对象this.
    public synchronized void output2(String name) {
        int len = name.length();
        for (int i = 0; i < len; i++) {
            System.out.print(name.charAt(i));
        }
        System.out.println();
    }

    public static synchronized void output3(String name) {
        int len = name.length();
        for (int i = 0; i < len; i++) {
            System.out.print(name.charAt(i));
        }
        System.out.println();
    }
}
