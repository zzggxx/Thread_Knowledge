package com.example.will.thread_knowledge;

public class ShardData {

    private int data = 100;

    public void decrement() {
        data--;
        System.out.println(Thread.currentThread().getName() + "..." + data);
    }

    public void inrement() {
        data++;
        System.out.println(Thread.currentThread().getName() + "..." + data);
    }
}
