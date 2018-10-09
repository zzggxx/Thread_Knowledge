package com.example.will.thread_knowledge;

public class ClassA {
    public void getData() {
        MyThreadLocalData data = MyThreadLocalData.newInstance();
        System.out.println("A_" + data.getName() + "_" + data.getAge());
    }
}
