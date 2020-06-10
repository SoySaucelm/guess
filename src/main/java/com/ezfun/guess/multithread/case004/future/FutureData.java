package com.ezfun.guess.multithread.case004.future;

/**
 * @author SoySauce
 * @date 2019/10/14
 */
public class FutureData implements Data {
    //FutureData是RealData的包装
    protected RealData realData = null;
    protected boolean isReady = false;

    public synchronized void setRealData(RealData realData) {
        if (isReady) {
            return;
        }
        this.realData = realData;
        isReady = true;
        //RealData已经被注入,通知getResult
        notifyAll();
    }

    /**
     * 会等待RealData构造完成
     *
     * @return
     */
    @Override
    public synchronized String getResult() {
        while (!isReady) {
            try {
                System.out.println("waiting realData...");
                //一直等待,直到RealData被注入
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("waiting end!!!");
        return realData.getResult();
    }
}
