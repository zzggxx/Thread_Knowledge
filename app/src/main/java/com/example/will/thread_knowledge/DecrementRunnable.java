package com.example.will.thread_knowledge;

public class DecrementRunnable implements Runnable {

    private ShardData shardData;

    public DecrementRunnable(ShardData shardData) {
        this.shardData = shardData;
    }

    @Override
    public void run() {
        while (true) {
            shardData.decrement();
        }
    }
}
