package com.ezfun.guess.multithread.case004.pstream;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author SoySauce
 * @date 2019/10/14
 */
public class Multiply implements Runnable{
    public static BlockingQueue<Msg> queue = new LinkedBlockingQueue<>();
    @Override
    public void run() {
        while (true){
            try {
                Msg msg = queue.take();
                msg.i = msg.i * msg.j;
                Div.queue.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
