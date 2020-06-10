package com.ezfun.guess.other;

import com.ezfun.guess.packagedemo.Client;

/**
 * @author SoySauce
 * @date 2019/10/10
 */
public class AnonymousInterClassDemo {

    public void print() {
        System.out.println("default...");
        new Client().run();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.run();

        AnonymousInterClassDemo demo = new AnonymousInterClassDemo() {
            @Override
            public void print() {
                System.out.println("****plus...");
            }
        };
        demo.print();
    }
}
