package com.ezfun.guess.thread;

/**
 * @author SoySauce
 * @date 2019/5/9
 */
public class Test {
    public static void main(String[] args) {
        int x = 0;
        for (int i = 0; i < 2; i++) {
            while (true) {
                System.out.println();
                if (++x == 2) {
                    System.out.println(x);
                    break;
                }
            }
        }
    }
}
