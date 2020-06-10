package com.ezfun.guess.multithread.case004.future;

/**
 * 最终需要使用的数据模型.使用sleep模拟
 *
 * @author SoySauce
 * @date 2019/10/14
 */
public class RealData implements Data {
    private final String result;

    public RealData(String param) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append(param);
        }
        //使用sleep模拟
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.result = sb.toString();
    }

    @Override
    public String getResult() {
        return result;
    }
}
