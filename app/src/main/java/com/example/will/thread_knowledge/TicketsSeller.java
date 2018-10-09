package com.example.will.thread_knowledge;

public class TicketsSeller extends Thread {

    //不是静态的话,四个线程每人都会有一个成员变量,下边的锁对象也是一样,必须是四个对象所公共的,最
    // 直接的锁对象是Ticks.class.尽量使用字节码文件作为锁对象.
    private static int tickets = 10000;//票越多就会更容易的观察到每一个窗口都会进行买票的,因为具有随机性.

//    static Object obj = new Object();

    public TicketsSeller(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
//            synchronized (obj) {
            synchronized (TicketsSeller.class) {
                if (tickets <= 0)
                    break;
                try {
                    //线程1睡,线程2睡,线程3睡,线程4睡,最后按照睡觉的先后顺序醒来执行下边的数据.模拟其他的操作
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(getName() + "...这是第" + tickets-- + "号票");
            }
        }
    }
}
