package com.ezfun.guess.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SoySauce
 * @date 2019/5/9
 */
public class ThreadLocalDemo {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static final String YMD = "yyyyMMdd";
    public static final String HMS = "HH:mm:ss";
    public static final String ALL = "yyyy-MM-dd HH:mm:ss";

    public static ThreadLocal<Map<String, SimpleDateFormat>> threadLocal = new ThreadLocal() {
        @Override
        protected Map initialValue() {
            Map<String, SimpleDateFormat> map = new HashMap<>(16);
            map.put(HMS, new SimpleDateFormat(HMS));
            map.put(YMD, new SimpleDateFormat(YMD));
            map.put(ALL, new SimpleDateFormat(ALL));
            return map;
        }
    };

    public static String DateFormat(String pattern, Date date) {
        return threadLocal.get().get(pattern).format(date);
    }

    public static Date DateParse(String pattern, String date) throws ParseException {
        return threadLocal.get().get(pattern).parse(date);
    }

    static class ParseDate implements Runnable {
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                Date parse = DateParse(ALL, "2019-05-09 08:59:" + i % 60);
                System.out.println("data: " + parse);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main2(String[] args) {
//        ThreadPoolExecutor pool = new ThreadPoolExecutor(10, 100,
//                0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue(), (ThreadFactory) Thread::new);
//        for (int i = 0; i < 1000; i++) {
//            pool.execute(new ParseDate(i));
//        }
//        pool.shutdown();
        temp:
        {
            int x = 99;
            System.out.println("temp start" + x);
            for (; ; ) {
                for (int i = 0; ; i++) {
                    System.out.println(i);
                    if (i == 5) {
                        break temp;
                    }
                }
            }
        }
        System.out.println("end");

    }




    public static void main1(String[] args) {


        String strSearch = "This is the string in which you have to search for a substring.";
        String substring = "substring";
        boolean found = false;
        int max = strSearch.length() - substring.length();
        testlbl:
        for (int i = 0; i <= max; i++) {
            int length = substring.length();
            int j = i;
            int k = 0;
            while (length-- != 0) {
                if (strSearch.charAt(j++) != substring.charAt(k++)) {
                    System.out.println("in " + i);
                    break testlbl;
                }
            }
            found = true;
            System.out.println("out" + i);
//            break testlbl;
        }
        if (found) {
            System.out.println("fund");
        } else {
            System.out.println("un fund");
        }
    }
}
