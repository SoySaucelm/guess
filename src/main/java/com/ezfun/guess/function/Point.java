package com.ezfun.guess.function;

import java.util.concurrent.locks.StampedLock;

/**
 * @author SoySauce
 * @date 2019/12/5
 */
public class Point {
    private double x, y;
    //StampedLock 读写锁的改进
    private final StampedLock sl = new StampedLock();

    /**
     * 这是一个排它锁
     *
     * @param deltaX
     * @param deltaY
     */
    void move(double deltaX, double deltaY) {
        long stamp = sl.writeLock();//申请写锁
        try {
            x += deltaX;
            y += deltaY;
        } finally {
            sl.unlockWrite(stamp);//释放读锁
        }
    }

    /**
     * 只读方法
     *
     * @return
     */
    double distanceFromOrigin() {
        //试图尝试一次乐观读 返回一个类似时间戳的邮戳整数stamp 这个stamp就可以作为这一次锁获取的凭证
        long stamp = sl.tryOptimisticRead();
        double currentX = x, currentY = y;
        //判断这个stamp是否在读过程发生期间被修改,如若没有则认为是有效 反之 则意味着在读取过程
        //可能被其它线程改写了数据,因此 有可能出现了脏读.如果出现这种情况,我们可以像处理CAS操作那样在一个死循环中一直使用乐观读
        //直到成功为止.也可以升级锁的级别
        if (!sl.validate(stamp)) {
            //判断乐观读失败 升级锁的级别 将乐观锁变为悲观锁
            //使用readLock()获得悲观的读锁,并进一步读取数据.如果当前对象正在被修改,则读锁的申请可能导致线程挂起
            stamp = sl.readLock();
            try {
                currentX = x;
                currentY = y;
            } finally {
                sl.unlockRead(stamp); //释放写锁
            }
        }
        return Math.sqrt(currentX * currentX + currentY * currentY);
    }
}
