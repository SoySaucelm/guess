package com.ezfun.guess.akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * @author SoySauce
 * @date 2020/1/3
 */
@SuppressWarnings("all")
public class HelloWorld extends UntypedActor {
    ActorRef greeter;

    /**
     * akka回调方法,在actor启动前会被akka框架调用完成一些初始化工作
     */
    @Override
    public void preStart() {
        greeter = getContext().actorOf(Props.create(Greeter.class), "greeter_name");
        System.out.println("Greeter Actor Path:" + greeter.path());
        greeter.tell(Greeter.Msg.GREET, getSelf());
    }

    @Override
    public void onReceive(Object msg) throws Exception {
        System.out.println(msg.getClass().getTypeName());
        if (msg == Greeter.Msg.DONE) {
            greeter.tell(Greeter.Msg.GREET, getSelf());
            greeter.tell(Greeter.Msg.GREET, getSelf());
            greeter.tell(Greeter.Msg.GREET, getSelf());
            getContext().stop(getSelf());
        } else {
            unhandled(msg);
        }
    }
}
