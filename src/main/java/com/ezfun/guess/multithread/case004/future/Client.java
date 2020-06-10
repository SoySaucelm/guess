package com.ezfun.guess.multithread.case004.future;

/**
 * @author SoySauce
 * @date 2019/10/14
 */
public class Client {
    public Data request(final String queryStr) {
        final FutureData future = new FutureData();
        new Thread() {
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                future.setRealData(realData);
            }
        }.start();
        //futureData会被立即返回
        return future;
    }
}
