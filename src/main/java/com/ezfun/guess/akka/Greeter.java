package com.ezfun.guess.akka;


import akka.actor.UntypedActor;

/**
 * @author SoySauce
 * @date 2020/1/3
 */
public class Greeter extends UntypedActor {
    public static enum Msg {
        /**
         * 欢迎,完成
         */
        GREET, DONE
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        if (msg == Msg.GREET) {
            System.out.println("hello world!  Msg.GREET");
            getSender().tell(Msg.DONE, super.getSelf());
        } else {
            unhandled(msg);
        }

    }
}
