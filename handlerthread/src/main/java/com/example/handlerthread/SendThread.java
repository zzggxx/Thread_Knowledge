package com.example.handlerthread;

import android.os.Handler;
import android.os.SystemClock;

public final class SendThread extends Thread {

    private Handler mHandler;

    SendThread(Handler handler) {
        this.mHandler = handler;
    }

    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 3; i++) {
            mHandler.sendEmptyMessage(0x1);
            SystemClock.sleep(1000);
        }
    }
}