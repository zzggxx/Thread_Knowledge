package com.example.will.thread_knowledge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*--------------线程的两种方法----------------------*/
//        threadCommonMethod();

        /*--------------timer定时器----------------------*/
        /*-----timer定时器普通方法------*/
//        TimerCommonMethod();
        /*-----timer定时器交替执行------*/
//        timerTaskMethod1();

//        while (true) {
//            try {
//                Thread.sleep(1000);
//                System.out.println("time_" + new Date().getSeconds() + "\n");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }

        /*--------------线程安全的问题----------------------*/
//        synchronizedThread();
        /*--------------线程间通讯的问题----------------------*/
        notifyAndWait();


    }

    private void notifyAndWait() {
        /*多个线程并发执行时, 在默认情况下CPU是随机切换线程的如果我们希望他们有规律的执行, 就可以使用通信, 例如每个线程执行一次打印*/
        final Business business = new Business();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    business.mian(i);
                }
            }
        }).start();

        for (int i = 0; i < 5; i++) {
            business.sub(i);
        }
    }

    private void synchronizedThread() {
        final Outputer outputer2 = new Outputer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer2.output2("zhougaoxiong");
                }
            }
        }).start();
        final Outputer outputer3 = new Outputer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer3.output3("will");
                }
            }
        }).start();
    }

    static class Outputer {
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

    /**
     * 子炸弹炸的时候又埋了一个炸弹,必须的重新埋炸弹,炸弹只能调度一次.
     */
    private void timerTaskMethod1() {
        class MyTimerTask extends TimerTask {

            @Override

            public void run() {
                count = (++count) % 2;
                System.out.println("time_bombing_" + new Date().getSeconds() + "\n");
                new Timer().schedule(new MyTimerTask(), 2000 + 2000 * count);
            }
        }

        new Timer().schedule(new MyTimerTask(), 0);
    }

    /**
     * timer定时器
     */
    private void TimerCommonMethod() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("thread_1 bombing" + Thread.currentThread().getName() + "\n");
            }
        }, 1000, 3000);//第一次炸弹炸,以后每个多久炸一次.点出参数看效果.

    }

    private void threadCommonMethod() {
        new Thread() {
            @Override
            public void run() {
//                super.run();
                System.out.println("thread_1 " + Thread.currentThread().getName());
            }
        }.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread_2 " + Thread.currentThread().getName());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread_3 " + Thread.currentThread().getName());
            }
        }) {
            @Override
            public void run() {
//                super.run();   //去掉后就不打印thread_3 了
                System.out.println("thread_4 " + Thread.currentThread().getName());
            }
        }.start();
    }
}