package com.ezfun.guess.akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;
import org.springframework.core.io.FileSystemResource;

import java.io.IOException;
import java.util.Properties;

/**
 * @author SoySauce
 * @date 2020/1/3
 */
public class AkkaTest {
    public static void main2(String[] args) {
        ActorSystem system = ActorSystem.create("ROOT", ConfigFactory.load("samplehello.conf"));
        ActorRef actorRef = system.actorOf(Props.create(HelloWorld.class), "TEST");
        ActorRef greeter = system.actorOf(Props.create(Greeter.class), "greeter_name_main");
        greeter.tell(Greeter.Msg.GREET, greeter);
        actorRef.tell(Greeter.Msg.GREET, greeter);
        System.out.println("hello world Actor Path:" + actorRef.path());
        system.stop(actorRef);


    }

    public static void main(String[] args) throws IOException {
        FileSystemResource r = new FileSystemResource( "pom.properties");
        Properties config2 = new Properties();
        config2.load(r.getInputStream());
        System.out.println("pom 版本2:"+config2.getProperty("version"));

        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        String path = systemClassLoader.getResource("").getPath().replace("/classes", "");
        FileSystemResource fileSystemResource = new FileSystemResource(path + "maven-archiver" + "/pom.properties");
        Properties config = new Properties();
        config.load(fileSystemResource.getInputStream());
        System.out.println("pom 版本:"+config.getProperty("version"));
    }
}
