package com.ezfun.guess.function;

import java.util.stream.IntStream;

/**
 * @author SoySauce
 * @date 2019/11/29
 */
public class PrimeUtil {
    public static boolean isPrime(int number) {
        int tmp = number;
        if (tmp < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(tmp); i++) {
            if (tmp % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args){
        boolean prime = isPrime(97);
        System.out.println(prime);
//        IntStream.range(1, 10000).filter(PrimeUtil::isPrime).forEach(System.out::println);
        long count = IntStream.range(1, 10000).filter(PrimeUtil::isPrime).count();
        System.out.println(count);
        long count2 = IntStream.range(1, 10000).parallel().filter(PrimeUtil::isPrime).count();
        System.out.println(count2);
    }
}
