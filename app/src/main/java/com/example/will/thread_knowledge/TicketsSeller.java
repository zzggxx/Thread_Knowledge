package com.example.will.thread_knowledge;

public class TicketsSeller extends Thread {

    //不是静态的话,四个线程每人都会有一个成员变量,下边的锁对象也是一样,必须是四个对象所公共的,最直接的锁对象是Ticks.class.尽量使用字节码文件作为锁对象.
    private static int tickets = 10000;

    static Object obj = new Object();

    public TicketsSeller() {
        super();

    }

    public TicketsSeller(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (obj) {
                if (tickets <= 0)
                    break;
                /*try {                 //票足够多的时候无论是不是睡觉都无所谓了,因为你sleep还是照样拿着锁.
                    Thread.sleep(1);    //线程1睡,线程2睡,线程3睡,线程4睡,最后按照睡觉的先后顺序醒来执行下边的数据.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                System.out.println(getName() + "...这是第" + tickets-- + "号票");
            }
        }
    }
}
