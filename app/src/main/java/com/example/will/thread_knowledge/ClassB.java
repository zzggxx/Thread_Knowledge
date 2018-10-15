package com.example.will.thread_knowledge;

public class ClassB {
    public void getData() {
        MyThreadLocalData data = MyThreadLocalData.newInstance();
        System.out.println("B_" + data.getName() + "_" + data.getAge());
    }
}
