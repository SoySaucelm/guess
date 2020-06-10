package com.ezfun.guess.multithread.case004.pstream;

/**
 * @author SoySauce
 * @date 2019/10/14
 */
public class PStreamMain {
    public static void main(String[] args) {
        new Thread(new Plus()).start();
        new Thread(new Multiply()).start();
        new Thread(new Div()).start();
        Msg msg;
        for (int i = 0; i <= 1000; i++) {
            for (int j = 0; j <= 1000; j++) {
                msg = new Msg();
                msg.i = i;
                msg.j = j;
                msg.orgStr = "((" + i + "+" + j + ")*" + i + "/2)";
                Plus.queue.add(msg);
            }
        }
    }
}
