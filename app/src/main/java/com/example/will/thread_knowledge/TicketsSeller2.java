package com.example.will.thread_knowledge;

public class TicketsSeller2 implements Runnable {

    private /*static*/ int count = 10000;//因为只有一个runnable所以不需要进行static.

    @Override
    public void run() {
        while (true) {
            synchronized (TicketsSeller2.class) {
                if (count == 0) {
                    break;
                }
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "..." + count-- + "号票");
            }
        }
    }
}
