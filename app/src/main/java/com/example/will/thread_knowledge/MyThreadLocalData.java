package com.example.will.thread_knowledge;

public class MyThreadLocalData {

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
