package com.example.will.thread_knowledge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
