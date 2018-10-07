package com.example.will.thread_knowledge;

/**
 * 用到共同数据(包括同步锁)或者共同的算法的若干个方法写到同一个类中,体现了高内聚和程序的健壮性.
 */
class Business {
    private boolean isShowSub;

    public synchronized void mian(int i) {
        while (isShowSub) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < 6; j++) {
            System.out.println(Thread.currentThread().getName() + "__" + j + "__loop of__" + i);
        }
        isShowSub = !isShowSub;
        this.notify();              //随机唤醒单个等待的线程
    }

    public synchronized void sub(int i) {
//        if (!isShowSub) {
        while (!isShowSub) {
            try {
                //第一次进来等待,交给别人改了变量之后,又返回判断语句确定一下变量是不是该我了,比if更牢靠(因为
                // 别人没notify()自己却醒了,就像做梦不是别人叫醒了而是自己醒了)
                // 体现了健壮性.
                this.wait();// synchronized 和 this使用的同一个对象不然报异常.
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int j = 0; j < 5; j++) {
            System.out.println(Thread.currentThread().getName() + "__" + j + "__loop of__" + i);
        }
        isShowSub = !isShowSub;
        this.notify();
    }
}
