package com.example.will.thread_knowledge;

public class Incrementrunnable implements Runnable {

    private ShardData shardData;

    public Incrementrunnable(ShardData shardData) {
        this.shardData = shardData;
    }

    @Override
    public void run() {
        while (true) {
            shardData.inrement();
        }
    }
}
