package com.example.will.thread_knowledge;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*--------------线程的两种方法-------------------------------------------------------------*/
//        threadCommonMethod();



        /*--------------timer定时器---------------------------------------------------------------*/
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



        /*--------------线程安全的问题-------------------------------------------------------------*/
//        synchronizedThread();



        /*--------------线程间通讯的问题-----------------------------------------------------------*/
        /*----主和子线程交替打印,设计模式很好----*/
//        notifyAndWait();
        /*----经典的买票问题,注意两种方式的区别----*/
        // 使用Thread方式
//        ticketsSeller();
        // 使用Runnable对象方式
//        ticketsSeller2();

        /*----线程内变量共享问题----*/
//        commonShareThreadLocal();



        /*--------------线程组--------------------------------------------------------------------*/
//        threadGroup();
        /*--------------线程组-------tag1-------------------------------------------------------------*/

    }

    /**
     * 作用:每一个线程的值独立存起来互不影响
     * 应用场景:数据库的事务,a给b打钱,同时c给d打钱,都是使用了事务但是有一样,小心commit的不是对应的begintransition
     */
    private void commonShareThreadLocal() {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int data = new Random().nextInt();
                    MyThreadLocalData.newInstance().setName("Name_" + data);
                    MyThreadLocalData.newInstance().setAge(data);
                    new ClassA().getData();
                    new ClassB().getData();
                }
            }).start();
        }
    }

    private void ticketsSeller() {
        TicketsSeller t1 = new TicketsSeller("窗口1");
        TicketsSeller t2 = new TicketsSeller("窗口2");
        TicketsSeller t3 = new TicketsSeller("窗口3");
        TicketsSeller t4 = new TicketsSeller("窗口4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }

    private void ticketsSeller2() {
        TicketsSeller2 t1 = new TicketsSeller2();

        new Thread(t1).start();
        new Thread(t1).start();
        new Thread(t1).start();
        new Thread(t1).start();
    }

    public void threadGroup() {

        MyRunnable mr = new MyRunnable();
        Thread t1 = new Thread(mr, "张三");
        ThreadGroup tg1 = t1.getThreadGroup();
        System.out.println(tg1.getName());                //默认的是主线程

        ThreadGroup tg = new ThreadGroup("我是一个新的线程组");        //创建新的线程组
        Thread t2 = new Thread(tg, mr, "张三1");                    //将线程t1放在组中
        System.out.println(t2.getThreadGroup().getName());        //获取组名
        System.out.println(t2.getName());        //获取线程名字
        System.out.println(Thread.currentThread().getName());        //获取当前线程名字

        tg.setDaemon(true);
    }

    private void notifyAndWait() {
        /*多个线程并发执行时, 在默认情况下CPU是随机切换线程的如果我们希望他们有规律的执行, 就
        可以使用通信, 例如每个线程执行一次打印*/
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

//        两种方式
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

//        辨别下
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
