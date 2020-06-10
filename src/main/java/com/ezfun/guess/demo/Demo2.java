package com.ezfun.guess.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author SoySauce
 * @date 2019/4/29
 */
public class Demo2 {

    public StringBuffer sb;
    private static final String PY_URL = System.getProperty("user.dir") + "\\src\\main\\java\\com\\ezfun\\guess\\py\\test.py";

    public void execute() throws Exception {
        Process process = Runtime.getRuntime().exec("python " + PY_URL);
        sb = new StringBuffer();
        ReadUtil readUtil = new ReadUtil(process.getInputStream(), sb);
        readUtil.start();
        process.waitFor();
    }


    class ReadUtil implements Runnable {
        private StringBuffer stringBuffer;
        private InputStream in;

        public ReadUtil(InputStream in, StringBuffer sb) {
            this.stringBuffer = sb;
            this.in = in;
        }

        public void start() {
            Thread thread = new Thread(this);
            // 将其设置为守护线程
            thread.setDaemon(true);
            thread.start();
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }

        @Override
        public void run() {
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = null;
            try {
                while ((line = br.readLine()) != null) {
                    stringBuffer.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
//            stringBuffer.append("ni").append("hao");
//            printContent();
        }
    }

    public void printContent() {
        System.out.println(sb.toString());
    }


    static class CloneableClass implements Cloneable {
        @Override
        public Object clone() throws CloneNotSupportedException {
            // TODO Auto-generated method stub
            return super.clone();
        }
    }
//
//    public <T> List test1(T<? super Cloneable> t) {
//        NCopies nCopies = (seed, num) -> {
//            List<T> list = new ArrayList<>();
//            for (int i = 0; i < num; i++) {
//                list.add(seed.clone());
//            }
//            return list;
//        };
//        List<T> copies = nCopies.getCopies(t, 5);
//        return copies;
//    }

    public static void main2(String[] args) throws Exception {
//        Demo2.class.newInstance().test1(new CloneableClass());
        Demo2 demo2 = Demo2.class.newInstance();
        demo2.execute();
        demo2.printContent();
        Demo2 demo21 = new Demo2();
        demo21.execute();
        demo21.printContent();
//        demo2.printContent();
    }










    public static void main22(String[] args) throws Exception {
        int x = 0;
        x=x++;
        System.out.println(x);
//            x++;
//            System.out.println(x);
    }
}
