package com.example.handlerthread;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 自己一步步的创建非主线程的处理方法
        methodFirst();

        // Google封装好的线程消息交换
        methodSecond();
    }

    private void methodSecond() {

        // 显示文本
        final TextView text = (TextView) findViewById(R.id.text1);

        // 创建与主线程关联的Handler
        final Handler mainHandler = new Handler();

        /**
         * 步骤1：创建HandlerThread实例对象
         * 传入参数 = 线程名字，作用 = 标记该线程
         */
        final HandlerThread mHandlerThread = new HandlerThread("handlerThread");

        /**
         * 步骤2：启动线程
         */
        mHandlerThread.start();

        /**
         * 步骤3：创建工作线程Handler & 复写handleMessage（）
         * 作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
         * 注：消息处理操作（HandlerMessage（））的执行线程 = mHandlerThread所创建的工作线程中执行
         */

        final Handler workHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            // 消息处理的操作
            public void handleMessage(Message msg) {
                //设置了两种消息处理操作,通过msg来进行识别
                Log.i(TAG, "handleMessage: 正在干活" + Thread.currentThread().getName());
                switch (msg.what) {
                    // 消息1
                    case 1:
                        try {
                            //延时操作
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                text.setText("我爱学习");
                            }
                        });
                        break;

                    // 消息2
                    case 2:
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                text.setText("我不喜欢学习");
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };

        /**
         * 步骤4：使用工作线程Handler向工作线程的消息队列发送消息
         * 在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
         */
        // 点击Button1
        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 通过sendMessage（）发送
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 1; //消息的标识
                msg.obj = "A"; // 消息的存放
                // b. 通过Handler发送消息到其绑定的消息队列
                workHandler.sendMessage(msg);
            }
        });

        // 点击Button2
        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 通过sendMessage（）发送
                // a. 定义要发送的消息
                Message msg = Message.obtain();
                msg.what = 2; //消息的标识
                msg.obj = "B"; // 消息的存放
                // b. 通过Handler发送消息到其绑定的消息队列
                workHandler.sendMessage(msg);
            }
        });

        // 点击Button3
        // 作用：退出消息循环
        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandlerThread.quit();
            }
        });
    }

    /*主线程到子线程的数据交互*/
    private void methodFirst() {
        final WorkThread workThread = new WorkThread();
        workThread.start();

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                workThread.getHandler().obtainMessage(1, "a").sendToTarget();

            }
        });
    }
}
