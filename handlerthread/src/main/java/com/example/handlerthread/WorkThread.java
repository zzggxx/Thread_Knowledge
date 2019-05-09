package com.example.handlerthread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/**
 * 非主线程的消息建立流程
 */
public class WorkThread extends Thread {

    private Handler mHandler;

    public Handler getHandler() {
        return mHandler;
    }

    public void quit() {
        mHandler.getLooper().quit();
    }

    @Override
    public void run() {
        super.run();
        //  第一步
        //创建该线程对应的Looper,
        // 1。new Looper（）
        // 2。将1步中的lopper 放在ThreadLocal里，ThreadLocal是保存数据的，主要应用场景是：线程间数据互不影响的情况
        // 3。在1步中的Looper的构造函数中new MessageQueue();
        //其实就是创建了该线程对用的Looper，Looper里创建MessageQueue来实现消息机制
        //对消息机制不懂得同学可以查阅资料，网上很多也讲的很不错。
        Looper.prepare();

        //  第二步
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.d("WorkThread", (Looper.getMainLooper() == Looper.myLooper()) + "," + msg.what);
            }
        };

        //  第三步
        //开启消息的死循环处理即：dispatchMessage
        Looper.loop();

        //  注意这3个的顺序不能颠倒
        Log.d("WorkThread", "end");//因为是阻塞,所以除非终止,其他情况下是绝对不会打印这句话的.
    }
}