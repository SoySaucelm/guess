package com.ezfun.guess.demo;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.*;

/**
 * @author SoySauce
 * @date 2019/10/11
 */
@Data
public class TransientDemo implements Serializable {

    private static final long serialVersionUID = -8397753853125569932L;
    private String name;
    private  String pwd = "123";

    @Override
    public String toString() {
        return "TransientDemo{" +
                "name='" + name + '\'' +
                ", pwd='" + pwd + '\'' +
                '}';
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String fileName = "transientDemo.txt";
        TransientDemo d = new TransientDemo();
        d.setName("张三");
//        TransientDemo.pwd = "1234";
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
        out.writeObject(d);
        out.flush();
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
        TransientDemo obj = (TransientDemo) in.readObject();
        System.out.println(obj);
        System.out.println(JSON.toJSONString(d));
    }

}
